import React from 'react';
import axios from 'axios';

class DownloadFile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            fileName: '',
        };
    }

    handleChange = (event) => {
        this.setState({ fileName: event.target.value });
    }

    handleDownload = () => {
        const { fileName } = this.state;
        axios({
            url: `http://localhost:8082/download/${fileName}`,
            method: 'GET',
            responseType: 'blob', // 指定响应类型为二进制流
        }).then((response) => {
            // 创建一个URL对象，用于生成下载链接
            const url = window.URL.createObjectURL(new Blob([response.data]));
            // 创建一个<a>标签，用于触发下载
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', fileName);
            document.body.appendChild(link);
            link.click();
        });
    }

    render() {
        const { fileName } = this.state;
        return (
            <div>
                <input type="text" value={fileName} onChange={this.handleChange} />
                <button onClick={this.handleDownload}>Download</button>
            </div>
        );
    }
}

export default DownloadFile;
