import * as React from 'react';
import Box from "@mui/material/Box";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Cloud from "@mui/icons-material/Cloud";
import Share from "@mui/icons-material/Share";
import Home from "@mui/icons-material/Home";
import TabPanel from "./TabPanel";
import VerticalTabscss from './ComponentCSS/VerticalTabscss.css'

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
import {useLocation, useParams} from "react-router-dom";


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
    const token = searchParams.get('token');

    const [open, setOpen] = React.useState(true);
    const handleClick = () => {
        setOpen(!open);
    };
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
                                {token}
                            </ListSubheader>
                        }>
                        <ListItemButton sx={{
                            borderRadius: '20px'
                        }}>
                            <ListItemIcon>
                                <SendIcon />
                            </ListItemIcon>
                            <ListItemText primary="ME" />
                        </ListItemButton>
                        <ListItemButton sx={{
                            borderRadius: '20px'
                        }}>
                            <ListItemIcon>
                                <DraftsIcon />
                            </ListItemIcon>
                            <ListItemText primary="SHARE" />
                        </ListItemButton>
                        <ListItemButton onClick={handleClick} sx={{
                            borderRadius: '20px'
                        }}>
                            <ListItemIcon>
                                <InboxIcon />
                            </ListItemIcon>
                            <ListItemText primary="BOX" />
                            {open ? <ExpandLess /> : <ExpandMore />}
                        </ListItemButton>
                        <Collapse in={open} timeout="auto" unmountOnExit>
                            <List component="div" disablePadding>
                                <ListItemButton sx={{ pl: 4 }}>
                                    <ListItemIcon>
                                        <StarBorder />
                                    </ListItemIcon>
                                    <ListItemText primary="Starred" />
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
                            backgroundColor: 'primary.dark',
                            borderRadius: '20px',
                            '&:hover': {
                                backgroundColor: 'primary.main',
                                opacity: [0.9, 0.8, 0.7],
                            },
                        }}
                    >

                    </Box>

                </Grid>


            </Grid>


            {/*<Tabs*/}
            {/*    orientation="vertical"*/}
            {/*    variant="scrollable"*/}
            {/*    value={value}*/}
            {/*    onChange={handleChange}*/}
            {/*    aria-label="Vertical tabs example"*/}
            {/*    sx={{ borderRight: 1, borderColor: 'divider',background:'#092045' }}*/}
            {/*>*/}
            {/*    <Tab label="&nbsp;&nbsp;&nbsp;&nbsp;Box" {...a11yProps(0)} icon={<Cloud />} style={{*/}
            {/*        color: 'white',*/}
            {/*        fontWeight: 'bold',*/}
            {/*        fontSize:'20px',*/}
            {/*        height:'15vh',*/}
            {/*        width:'10vw',*/}
            {/*        display: "flex",*/}
            {/*        flexDirection: "row",*/}
            {/*        alignItems: "center",*/}
            {/*        justifyContent: "center",*/}
            {/*    }} />*/}
            {/*    <Tab label="&nbsp;Share" {...a11yProps(1)} icon={<Share />} style={{*/}
            {/*        color: 'white',*/}
            {/*        fontWeight: 'bold',*/}
            {/*        fontSize:'20px',*/}
            {/*        height:'15vh',*/}
            {/*        width:'10vw',*/}
            {/*        display: "flex",*/}
            {/*        flexDirection: "row",*/}
            {/*        alignItems: "center",*/}
            {/*        justifyContent: "center"*/}
            {/*    }} />*/}
            {/*    <Tab label="&nbsp;&nbsp;&nbsp;&nbsp;Me" {...a11yProps(2)} icon={<Home />} style={{*/}
            {/*        color: 'white',*/}
            {/*        fontWeight: 'bold',*/}
            {/*        fontSize:'20px',*/}
            {/*        height:'15vh',*/}
            {/*        width:'10vw' ,*/}
            {/*        display: "flex",*/}
            {/*        flexDirection: "row",*/}
            {/*        alignItems: "center",*/}
            {/*        justifyContent: "center"*/}
            {/*    }}/>*/}
            {/*    <div className="volum">*/}
            {/*        space*/}
            {/*    </div>*/}
            {/*</Tabs>*/}
            {/*<TabPanel value={value} index={0}>*/}
            {/*    存储空间*/}
            {/*</TabPanel>*/}
            {/*<TabPanel value={value} index={1}>*/}
            {/*    文件共享*/}
            {/*</TabPanel>*/}
            {/*<TabPanel value={value} index={2}>*/}
            {/*    我的*/}
            {/*</TabPanel>*/}



        </Box>
    );
}