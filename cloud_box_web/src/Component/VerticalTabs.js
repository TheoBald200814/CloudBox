import * as React from 'react';
import Box from "@mui/material/Box";

import ListSubheader from '@mui/material/ListSubheader';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Collapse from '@mui/material/Collapse';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import DraftsIcon from '@mui/icons-material/Drafts';
import SendIcon from '@mui/icons-material/Send';
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import StarBorder from '@mui/icons-material/StarBorder';
import Grid from "@mui/material/Unstable_Grid2";
import {styled} from "@mui/material/styles";
import Paper from "@mui/material/Paper";
import {Route, Switch, useLocation, useParams} from "react-router-dom";
import FileList from "../Home/CloudBox/FileList";
import Me from "../Home/Me/Me";


function a11yProps(index) {
    return {
        id: `vertical-tab-${index}`,
        'aria-controls': `vertical-tabpanel-${index}`,
    };
}


const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));



/**
 * CloudBox右侧导航栏
 * @param props 传入参数
 * @returns {JSX.Element}
 * @constructor
 */
export default function VerticalTabs(props) {

    const searchParams = new URLSearchParams(useLocation().search);
    //获取url携带参数
    const token = searchParams.get('token');
    //账户令牌
    const userId = searchParams.get('userId');
    //账户Id
    const nickname = searchParams.get('nickname');
    //账户昵称
    const authority = searchParams.get('authority');
    //账户权限
    const empty = searchParams.get('empty');
    //账户容量

    const [open, setOpen] = React.useState(true);

    const handleClick = () => {
        setOpen(!open);
    };

    const [componentToShow, setComponentToShow] = React.useState('component1');

    const handleClickPage = (component) => {
        setComponentToShow(component);
    }

    return (
        <Box
            sx={{
                height: '80vh',
                width:'100vw',
                backgroundColor: "rgba(255,255,255,0.06)",
                backdropFilter: "blur(5px)",
            }}
        >
            <Grid container  spacing={0}>
                <Grid xs={3} md={3}>

                    <List
                        sx={{
                            position: "fixed",
                            left:"2%",
                            margintop:"2%",
                            width: '22vw',
                            height:'78vh',
                            bgcolor: 'background.paper',
                            backgroundColor: "rgba(133,133,133,0.56)",
                            borderRadius: '20px'
                        }}
                        component="nav"
                        aria-labelledby="nested-list-subheader"
                        subheader={
                            <ListSubheader component="div" id="nested-list-subheader" sx={{
                                backgroundColor: "rgba(0,19,51,0.73)",
                                borderRadius: '20px',
                                color:"#ffffff",
                                fontSize:"100%"
                            }}>
                                {userId}
                            </ListSubheader>
                        }>

                        <ListItemButton sx={{
                            borderRadius: '20px'
                        }} onClick={() => handleClickPage('0')}>
                            <ListItemIcon>
                                <SendIcon />
                            </ListItemIcon>
                            <ListItemText primary="公告信息" />
                        </ListItemButton>

                        <ListItemButton sx={{
                            borderRadius: '20px'
                        }} onClick={() => handleClickPage('1')}>
                            <ListItemIcon>
                                <SendIcon />
                            </ListItemIcon>
                            <ListItemText primary="个人管理" />
                        </ListItemButton>

                        <ListItemButton sx={{
                            borderRadius: '20px'
                        }} onClick={() => handleClickPage('2')}>
                            <ListItemIcon>
                                <SendIcon />
                            </ListItemIcon>
                            <ListItemText primary="账户管理" />
                        </ListItemButton>

                        <ListItemButton sx={{
                            borderRadius: '20px'
                        }} onClick={() => handleClickPage('3')}>
                            <ListItemIcon>
                                <SendIcon />
                            </ListItemIcon>
                            <ListItemText primary="数据分析" />
                        </ListItemButton>

                        <ListItemButton sx={{
                            borderRadius: '20px'
                        }} onClick={() => handleClickPage('4')}>
                            <ListItemIcon>
                                <DraftsIcon />
                            </ListItemIcon>
                            <ListItemText primary="文件共享" />
                        </ListItemButton>

                        <ListItemButton onClick={handleClick} sx={{
                            borderRadius: '20px'
                        }}>
                            <ListItemIcon>
                                <InboxIcon />
                            </ListItemIcon>
                            <ListItemText primary="云盘空间" />
                            {open ? <ExpandLess /> : <ExpandMore />}
                        </ListItemButton>
                        <Collapse in={open} timeout="auto" unmountOnExit>
                            <List component="div" disablePadding>
                                <ListItemButton sx={{ pl: 4 }} onClick={() => handleClickPage('5')}>
                                    <ListItemIcon>
                                        <StarBorder />
                                    </ListItemIcon>
                                    <ListItemText primary="文件列表" />
                                </ListItemButton>
                            </List>
                        </Collapse>
                    </List>

                </Grid>

                <Grid xs={9} md={9}>

                    <Box
                        sx={{
                            marginTop:'0.5%',
                            width: '73vw',
                            height: '78vh',
                            backgroundColor: "rgba(133,133,133,0.56)",
                            borderRadius: '20px',
                        }}
                    >
                        <ChildPage pageNum = {componentToShow}
                                   token = {token}
                                   userId = {userId}
                                   nickname = {nickname}
                                   authority = {authority}
                                   empty = {empty}
                        />

                    </Box>


                </Grid>


            </Grid>



        </Box>
    );
}

class ChildPage extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            pageNum : props.pageNum,
            token:props.token,
            userId:props.userId,
            nickname:props.nickname,
            authority:props.authority,
            empty:props.empty
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.pageNum !== this.props.pageNum) {
            this.changePage(nextProps.pageNum)
        }
    }

    //函数里可以包含ajax请求等复杂的逻辑
    changePage(myArgument) {
        this.setState({
            pageNum: myArgument
        })
        console.log(this.state.pageNum);
    }


    render() {
        switch (this.state.pageNum){

            case '0':
                //公告信息
                return (
                    <div>

                       0

                    </div>
                );
                break;
            case '1':
                //个人管理
                return (
                    <div>
                        <Me token={this.state.token}
                            accountId={this.state.userId}
                            nickname={this.state.nickname}
                            authority={this.state.authority}
                            empty_={this.state.empty}
                        />
                    </div>
                );
                break;
            case '2':
                //账户管理
                return (
                    <div>
                        2
                    </div>
                );
                break;
            case '3':
                //数据分析
                return (
                    <div>
                        3
                    </div>
                );
                break;
            case '4':
                //文件共享
                return (
                    <div>
                        4
                    </div>
                );
                break;
            case '5':
                //云盘空间-文件列表
                return (
                    <div>
                        <FileList token = {this.state.token} />
                    </div>
                );
                break;
        }
    }

}


