import * as React from 'react';
import Box from "@mui/material/Box";
import Grid from "@mui/material/Unstable_Grid2";
import {Button, Input} from "@mui/material";
import axios from "axios";

export default class NicknameUpdate extends React.Component{

    constructor(props) {
        super(props);

        this.state = {
            token:this.props.token,
            newNickname:''
        }
        this.handleChangeNickname = this.handleChangeNickname.bind(this);
        this.handleNicknameUpdate = this.handleNicknameUpdate.bind(this);

    }

    /**
     * 昵称更新
     * @param event
     */
    handleChangeNickname(event){
        this.setState({
            newNickname:event.target.value
        })
    }

    /**
     * 昵称更新提交
     */
    handleNicknameUpdate(){

        const {token, newNickname} = this.state;

        if(newNickname !== ''){
            //建立请求
            axios.post("http://43.142.148.141:8081/updateNickname", {token: token, newNickname:newNickname}).then((response) => {
                //建立连接，更新昵称
                if(response.data.res === 'success'){
                    //若修改成功
                    alert("昵称修改成功");
                }else {
                    //若修改失败
                    alert("昵称修改失败");
                }
            });
        }

    }

    render() {

        return (
            <Box  sx={{
                marginTop:'0.5%',
                width:'30vw',
                height: '10vh',
                backgroundColor: "rgba(133,133,133,0.56)",
                borderRadius: '20px',
            }}>
                <Grid container >

                    <Grid xs={12} md={12}>
                        Change
                    </Grid>
                    <Grid xs={6} md={6}>
                        新昵称
                        <Input type='text' value={this.state.newNickname} onChange = {this.handleChangeNickname}/>
                    </Grid>
                    <Grid xs={12} md={12}>
                        <Button onClick = {this.handleNicknameUpdate} >Submit</Button>
                    </Grid>
                </Grid>
            </Box>
        );

    }

}