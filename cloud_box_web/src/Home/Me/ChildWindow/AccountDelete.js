import * as React from 'react';
import Box from "@mui/material/Box";
import Grid from "@mui/material/Unstable_Grid2";
import {Button, Input} from "@mui/material";
import axios from "axios";

export default class AccountDelete extends React.Component{

    constructor(props) {
        super(props);

        this.state = {
            token:this.props.token,
        }
        this.handleDeleteAccount = this.handleDeleteAccount.bind(this);
    }

    /**
     * 注销账户确认
     */
    handleDeleteAccount(){

        const {token} = this.state;
        //建立请求
        axios.post("http://localhost:8081/deleteAccount?token=" + token).then((response) => {
            //建立连接，注销账户
            if(response.data.res === 'success'){
                //注销成功
                alert("账户注销成功");
                window.location.assign(
                    '/MainPage'
                );
            }else {
                //注销失败
                alert("账户注销失败");
            }
        });
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
                        确认注销账户
                    </Grid>
                    <Grid xs={12} md={12}>
                        <Button onClick = {this.handleDeleteAccount} >确认</Button>
                        <Button  >取消</Button>
                    </Grid>
                </Grid>
            </Box>
        );

    }

}