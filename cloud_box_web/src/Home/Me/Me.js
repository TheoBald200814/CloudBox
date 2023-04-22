import React from 'react';
import Grid from "@mui/material/Unstable_Grid2";
import Avatar from "@mui/material/Avatar";
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import {Input} from "@mui/material";
import axios from "axios";
import CreateIcon from '@mui/icons-material/Create';
import AlertConfirm from "react-alert-confirm";
import FileUpdate from "../CloudBox/ChildWindow/FileUpdate";
import NicknameUpdate from "./ChildWindow/NicknameUpdate";
import AccountDelete from "./ChildWindow/AccountDelete";


export default class Me extends React.Component{

    constructor(props) {
        super(props);
        this.state = {

            accountId:props.accountId,
            photo: props.photo,
            nickname:props.nickname,
            authority:props.authority,
            empty_:props.empty_,
            token:props.token,
            password:'',
            newpassword_1:'',
            newpassword_2:'',
            newNickname:''
        }

        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.handleChangeNewPassword_1 = this.handleChangeNewPassword_1.bind(this);
        this.handleChangeNewPassword_2 = this.handleChangeNewPassword_2.bind(this);
        this.handleUpdatePassword = this.handleUpdatePassword.bind(this);
        this.handleChangeLogout = this.handleChangeLogout.bind(this);
        this.handleChangeDelete = this.handleChangeDelete.bind(this);
        this.handleChangeNickname = this.handleChangeNickname.bind(this);
    }

    handleChangePassword(event) {
        this.setState({password: event.target.value});
    }

    handleChangeNewPassword_1(event) {
        this.setState({newpassword_1: event.target.value});
    }

    handleChangeNewPassword_2(event) {
        this.setState({newpassword_2: event.target.value});
    }


    /**
     * 密码更新触发器
     */
    handleUpdatePassword() {
        const { password,newpassword_1,newpassword_2,token } = this.state;
        if(newpassword_1 === newpassword_2){
            //如果密码一致
            axios.post("http://43.142.148.141:8081/updatePassword", {token:token, password:newpassword_1}).then((response) => {
                if(response.data.res === 'success'){
                    alert("修改成功，请重新登陆")
                    this.handleChangeLogout();
                }else {
                    alert("密码修改失败")
                }
            });
        }else {
            alert("两次输入密码不一致，请重新输入");
        }
    }

    /**
     * 账户登出触发器
     */
    handleChangeLogout(){

        axios.post("http://43.142.148.141:8081/logoutAccount?token=" + this.state.token).then((response) => {
            if(response.data.res === "success"){
                alert("登出成功");
            }else {
                alert("登出失败");
            }
            window.location.assign(
                '/MainPage'
            );
        });
    }

    /**
     * 用户昵称修改弹框触发器
     * @param params
     * @returns {Promise<void>}
     */
    handleChangeNickname = async (params) => {

        const [action] = await AlertConfirm({
            maskClosable: true,
            custom: dispatch => (
                <div>
                    <NicknameUpdate token = {this.state.token} />
                </div>
            )
        });
    }

    /**
     * 账户注销触发器
     */
    handleChangeDelete = async (params) => {

        const [action] = await AlertConfirm({
            maskClosable: true,
            custom: dispatch => (
                <div>
                    <AccountDelete token = {this.state.token} />
                </div>
            )
        });
    }



    render() {
        return(

            <Grid container>
                <Grid xs={12} md={12} style={{
                    display:"flex",
                    justifyContent: "center",
                    alignItems: "center"
                }}>
                    <Paper elevation={3} sx={{
                        width:'50vw',
                        height:'30vh',
                        borderRadius:'50px',
                        display:"flex",
                        justifyContent: "center",
                        alignItems: "center"
                    }}>
                        <Grid container >
                            <Grid xs={12} md={12} style={{
                                display:"flex",
                                justifyContent: "center",
                                alignItems: "center"
                            }}>
                                <Avatar sx={{
                                    width:'80px',
                                    height:'80px'
                                }}>H</Avatar>
                            </Grid>
                            <Grid xs={12} md={12} style={{
                                display:"flex",
                                justifyContent: "center",
                                alignItems: "center"
                            }}>
                                <div style={{
                                    fontSize:'25px'
                                }}>
                                    <div onClick={this.handleChangeNickname}>
                                        {this.state.nickname + " "}
                                        <CreateIcon sx={{
                                            width:'20px',
                                            height:'20px',
                                        }}/>
                                    </div>

                                </div>
                            </Grid>
                            <Grid xs={12} md={12} style={{
                                display:"flex",
                                justifyContent: "center",
                                alignItems: "center"
                            }}>
                                <div style={{
                                    fontSize:'40px'
                                }}>
                                    {this.state.accountId}
                                </div>
                            </Grid>
                        </Grid>
                    </Paper>
                </Grid>
                <Grid xs={12} md={12} style={{
                    display:"flex",
                    justifyContent: "center",
                    alignItems: "center"
                }}>
                    <Grid container style={{
                        display:"flex",
                        justifyContent: "center",
                        alignItems: "center",
                        padding:'50px'
                    }}>
                        <Grid xs={6} md={6}>
                            账户属性
                            <br/>
                            {this.state.authority}
                            账户容量
                            <br/>
                            {this.state.empty_}



                        </Grid>
                        <Grid xs={6} md={6}>

                            重置密码
                            <br/>
                            <Input type="password" placeholder="请输入原密码" value={this.state.password} onChange={this.handleChangePassword}/>
                            <Input type="password" placeholder="请输入新密码" value={this.state.newpassword_1} onChange={this.handleChangeNewPassword_1}/>
                            <Input type="password" placeholder="请再次输入新密码" value={this.state.newpassword_2} onChange={this.handleChangeNewPassword_2}/>
                            <Button variant="contained" onClick={this.handleUpdatePassword}>重置</Button>

                        </Grid>
                    </Grid>
                </Grid>
                <Grid xs={12} md={12} style={{
                    display:"flex",
                    justifyContent: "center",
                    alignItems: "center",
                    padding:'10px'
                }}>
                    <Button variant="outlined" color="error" sx={{
                        width:'8vw',
                        height:'6vh',
                        borderRadius:'10px',
                        marginRight:'3vw',
                        fontSize:'20px'
                    }} onClick={this.handleChangeLogout}>退出</Button>
                    <Button variant="contained" color="error" sx={{
                        width:'8vw',
                        height:'6vh',
                        borderRadius:'10px',
                        marginLeft:'3vw',
                        fontSize:'20px'
                    }} onClick={this.handleChangeDelete}>注销</Button>

                </Grid>
            </Grid>
        );
    }


}