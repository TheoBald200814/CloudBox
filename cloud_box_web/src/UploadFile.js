import React from "react";
import axios from "axios";
import {Button, Input} from "@mui/material";

class UploadFile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            file: null
        };
    }

    handleFileChange = (event) => {
        this.setState({
            file: event.target.files[0]
        });
    };

    handleFileUpload = () => {
        const formData = new FormData();
        formData.append("file", this.state.file);
        axios.post("http://localhost:8082/test", formData).then((response) => {
            console.log(response.data);
        });
    };

    handleFileDownload = () => {


    }

    render() {
        return (
            <div>
                <Input type="file" onChange={this.handleFileChange} />
                <Button variant="contained" color="primary" onClick={this.handleFileUpload}>
                    上传文件23
                </Button>
            </div>
        );
    }
}

export default UploadFile;
