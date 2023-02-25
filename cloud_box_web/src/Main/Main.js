import * as React from "react";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Unstable_Grid2";
import {styled} from "@mui/material/styles";
import Paper from "@mui/material/Paper";
import Main from './Main.css'
import {NavigationBar} from "../Component/NavigationBar";
import SignInAndSignUp from "../Component/SignInAndSignUp";
import CloudBoxx from "../Home/CloudBox/FileList";
import {NavLink, Route, Switch,useHistory} from "react-router-dom";

const Item = styled(Paper)(({ theme }) => ({//测试脚手架
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));


/**
 * 云盘主页
 * @returns {JSX.Element}
 * @constructor
 */
export default function MainPage() {

    const history = useHistory();

    return (
        <body className="background">
        <Box sx={{ height:'200vh',width:'100vw'}}>

            <Grid container spacing={2}>
                <Grid xs={12} md={12} style={{height:'10vh',position:'fixed',top:'0',zIndex:'10'}} >
                    <NavigationBar />
                </Grid>
                {/*//导航栏*/}
                <Grid xs={12} md={12}>
                    <Box sx={{width:'100vw',height:'1vh'}}></Box>
                </Grid>
                {/*//占位间隔*/}
                <Grid xs={12} md={12}>
                    <Box sx={{height:'100vh'}}>
                        <Item>test</Item>
                        <text style={{fontSize:'1000%',color:'white',fontWeight: 900}}>
                            Welcome

                        </text>
                    </Box>
                </Grid>
                {/*//主标题*/}
                <Grid xs={6} md={6}>
                    <Item>xs=6 md=8</Item>
                    <Box className="detail_empty">
                        <text style={{fontSize:'400%',color:'white',fontWeight: 400}}>
                            Welcomes ds da,d sddd dsd  asawfa awvrv,vawv ds f damwk m mkewafnj njn ndajwn jajwd
                        </text>
                    </Box>
                </Grid>
                {/*//登陆层说明（居左）*/}
                <Grid xs={6} md={6}>
                    <Item>xs=6 md=8</Item>
                    <Box sx={{width:'30vw'}}>
                        <SignInAndSignUp/>
                    </Box>
                </Grid>
                {/*//登陆框（居右）*/}
                <Grid xs={6} md={6}>
                    <Item>版本1</Item>
                    <Box className="detail_left">
                        <text style={{fontSize:'400%',color:'white',fontWeight: 900}}>
                            这里放云盘的架构及设计理念
                        </text>
                    </Box>
                </Grid>
                {/*//文字解释（居左）*/}
                <Grid xs={6} md={6}>
                    <Item>版本2</Item>
                    <Box className="detail_right">
                        <text style={{fontSize:'400%',color:'white',fontWeight: 900}}>
                            这里放架构图
                        </text>
                    </Box>
                </Grid>
                {/*//文字解释（居右）*/}
                <Grid xs={12} md={12}>
                    <Item> 放最后的一些说明</Item>
                    <Box>

                    </Box>
                </Grid>
                {/*//最后收尾*/}
            </Grid>
        </Box>
        </body>
    );
}
