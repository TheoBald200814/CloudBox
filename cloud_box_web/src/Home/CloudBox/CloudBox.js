import * as React from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Unstable_Grid2';
import PropTypes from 'prop-types';
import Typography from '@mui/material/Typography';
import BoxTopTabs from "../../Component/BoxTopTabs";
import CloudBox from './CloudBox.css'
import VerticalTabs from "../../Component/VerticalTabs";


const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));
//======================================================================================
export default function CloudBoxx() {//Box框架

    return (
        <body className="cloud_box_background">
        <Box sx={{ height:'100vh',width:'100vw'}}>
            <Grid container spacing={2}>

                <Grid xs={12} md={12} style={{height:'10vh'}}>
                    <BoxTopTabs />
                </Grid>

                <Grid xs={12} md={12}>
                    <VerticalTabs />
                </Grid>

                <Grid xs={12} md={12}>
                    <Item>版本信息</Item>
                </Grid>
            </Grid>
        </Box>
        </body>

    );
}
//======================================================================================


