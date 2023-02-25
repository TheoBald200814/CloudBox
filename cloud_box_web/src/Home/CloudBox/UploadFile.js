import React from "react";
import axios from "axios";
import {Button, Input} from "@mui/material";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Unstable_Grid2";

/**
 * 文件上传类
 * 构造时传入token
 */
class UploadFile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            token:props.token,
            file: null,
            fileName:''
        };
    }

    /**
     * 文件更新函数
     * @param event
     */
    handleFileChange = (event) => {
        const files = event.target.files[0];
        const reader = new FileReader();
        this.setState({
            file: files,
            fileName:files.name
        });
    };

    /**
     * 文件上传函数
     */
    handleFileUpload = () => {
        const {token, fileName} = this.state;
        const formData = new FormData();
        formData.append("file", this.state.file);
        axios.post("http://localhost:8082/createFile?token=" + token + "&fileName=" + fileName, formData).then((response) => {
            console.log(response.data);
            if(response.data.res === 'success'){
                alert("文件上传成功");
            }else {
                alert("文件上传失败");
            }
        });
    };

    render() {
        return (
            <Grid container>

                <Grid xs={12} md={12}>
                    <Input type="file" onChange={this.handleFileChange} />
                </Grid>
                <Grid xs={12} md={12} sx={{
                    display:"flex",
                    justifyContent: "center",
                    alignItems: "center",
                    padding:'2vh'
                }}>
                    <Button variant="contained" color="primary" onClick={this.handleFileUpload}>
                        上传
                    </Button>
                </Grid>
            </Grid>
        );
    }
}

export default UploadFile;
