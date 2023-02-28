
import * as React from 'react';
import Box from "@mui/material/Box";
import Grid from "@mui/material/Unstable_Grid2";
import {Button, Input} from "@mui/material";
import axios from "axios";

export default class FileUpdate extends React.Component{

    constructor(props) {
        super(props);

        this.state = {
            token:this.props.token,
            fileName:this.props.fileName,
            newFileName:'',
            newFileType:''
        }

        this.handleChangeFileName = this.handleChangeFileName.bind(this);
        this.handleChangeFileType = this.handleChangeFileType.bind(this);
        this.handleFileUpdate = this.handleFileUpdate.bind(this);

    }

    /**
     * 新文件名更新
     * @param event
     */
    handleChangeFileName(event){
        this.setState({
            newFileName:event.target.value
        })
    }

    /**
     * 新文件类型更新
     * @param event
     */
    handleChangeFileType(event){
        this.setState({
            newFileType:event.target.value

        })
    }

    /**
     * 文件更新提交
     */
    handleFileUpdate(){

        const {token,fileName,newFileName} = this.state;

        //若需要更新文件名
        if(newFileName !== ''){
            //建立请求
            axios.post("http://localhost:8082/updateFileName", {token: token, fileName:fileName, newFileName:newFileName}).then((response) => {
                //建立连接，更新文件名
                if(response.data.res === 'success'){
                    //若修改成功
                    alert("文件名修改成功" + response.data.res);
                }else {
                    //若修改失败
                    alert("文件名修改失败" + response.data.res);
                }
            });
        }
        //若需要更新文件类型
        if(this.state.newFileType !== ''){
            //建立请求
            axios.post("http://localhost:8082/updateFileType", {token: this.state.token, fileName:this.state.fileName, newFileType:this.state.newFileType}).then((response) => {
                //建立连接，更新文件类型
                if(response.data.res === 'success'){
                    //若修改成功
                    alert("文件类型修改成功");
                }else {
                    //若修改失败
                    alert("文件类型修改失败");
                }
            });
        }

    }

    render() {

        return (
            <Box  sx={{
                marginTop:'0.5%',
                width:'30vw',
                height: '50vh',
                backgroundColor: "rgba(133,133,133,0.56)",
                borderRadius: '20px',
            }}>
                <Grid container >

                    <Grid xs={12} md={12}>
                        Change
                    </Grid>
                    <Grid xs={6} md={6}>
                        新文件名
                        <Input type='text' value={this.state.newFileName} onChange = {this.handleChangeFileName}/>
                    </Grid>
                    <Grid xs={6} md={6}>
                        新文件类型
                        <Input type='text' value={this.state.newFileType} onChange = {this.handleChangeFileType}/>
                    </Grid>
                    <Grid xs={12} md={12}>
                        <Button onClick = {this.handleFileUpdate} >Submit</Button>
                    </Grid>
                </Grid>
            </Box>
        );

    }

}