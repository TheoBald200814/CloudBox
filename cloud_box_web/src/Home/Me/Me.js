import React from 'react';
import Grid from "@mui/material/Unstable_Grid2";
import Avatar from "@mui/material/Avatar";
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import {Input} from "@mui/material";
import axios from "axios";


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
            newpassword_2:''
        }

        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.handleChangeNewPassword_1 = this.handleChangeNewPassword_1.bind(this);
        this.handleChangeNewPassword_2 = this.handleChangeNewPassword_2.bind(this);
    }



    handleChangePassowrd(event) {
        this.setState({password: event.target.value});
    }

    handleChangeNewPassowrd_1(event) {
        this.setState({newpassword_1: event.target.value});
    }

    handleChangeNewPassowrd_2(event) {
        this.setState({newpassword_2: event.target.value});
    }

    handleUpdatePassword(event) {
        const { password,newpassword_1,newpassword_2,token } = this.state;
        if(newpassword_1 === newpassword_2){
            //如果密码一致
            axios.post("http://localhost:8081/updatePassword", {token:token, password:newpassword_1}).then((response) => {
                console.log(response.data);
            });
        }else {
            alert("两次输入密码不一致，请重新输入");
        }
        event.preventDefault();
    }



    render() {
        return(

            <Grid container>
                <Grid sx={12} md={12} style={{
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
                            <Grid sx={12} md={12} style={{
                                display:"flex",
                                justifyContent: "center",
                                alignItems: "center"
                            }}>
                                <Avatar sx={{
                                    width:'80px',
                                    height:'80px'
                                }}>H</Avatar>
                            </Grid>
                            <Grid sx={12} md={12} style={{
                                display:"flex",
                                justifyContent: "center",
                                alignItems: "center"
                            }}>
                                <div style={{
                                    fontSize:'25px'
                                }}>
                                    {this.state.nickname}
                                </div>
                            </Grid>
                            <Grid sx={12} md={12} style={{
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
                <Grid sx={12} md={12} style={{
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
                        <Grid sx={6} md={6}>
                            账户属性
                            <br/>
                            {this.state.authority}
                            账户容量
                            <br/>
                            {this.state.empty_}



                        </Grid>
                        <Grid sx={6} md={6}>

                            重置密码
                            <br/>
                            <Input type="password" placeholder="请输入原密码" value={this.state.password} onChange={this.handleChangePassword}/>
                            <Input type="password" placeholder="请输入新密码" value={this.state.newpassword_1} onChange={this.handleChangeNewPassword_1}/>
                            <Input type="password" placeholder="请再次输入新密码" value={this.state.newpassword_2} onChange={this.handleChangeNewPassword_2}/>
                            <Button variant="contained">重置</Button>

                        </Grid>
                    </Grid>
                </Grid>
                <Grid sx={12} md={12} style={{
                    display:"flex",
                    justifyContent: "center",
                    alignItems: "center",
                    padding:'10px'
                }}>
                    <Button variant="outlined" color="error" sx={{
                        width:'8vw',
                        height:'8vh',
                        borderRadius:'10px',
                        marginRight:'3vw',
                        fontSize:'20px'
                    }}>退出</Button>
                    <Button variant="contained" color="error" sx={{
                        width:'8vw',
                        height:'8vh',
                        borderRadius:'10px',
                        marginLeft:'3vw',
                        fontSize:'20px'
                    }}>注销</Button>

                </Grid>
            </Grid>
        );
    }


}