import React from "react";
import axios from "axios";
import {Button, Input} from "@mui/material";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Unstable_Grid2";


/**
 * MainPage顶部导航栏
 * @returns {JSX.Element}
 * @constructor
 */
function NavigationBar() {//顶部导航栏
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <Box className="navigate_bar">
            <Grid container>
                <Grid xs={3} md={3}>
                    <div className="bold_smooth_text">
                        Cloud Box
                    </div>
                </Grid>
                {/*//左上角LOGO*/}
                <Grid xs={3} md={3}>
                    <div className="test">
                        <Button variant="text" sx={{
                            color: 'white',
                            fontWeight: '900',
                            fontSize: 'large'}}>Introduction</Button>
                        <Button variant="text" sx={{
                            color: 'white',
                            fontWeight: '900',
                            fontSize: 'large'}}>Text</Button>
                        <Button variant="text" sx={{
                            color: 'white',
                            fontWeight: '900',
                            fontSize: 'large'}}>Text</Button>
                    </div>
                </Grid>
                {/*//导航栏主选项（按钮）*/}
                <Grid xs={4} md={4}></Grid>
                {/*//占位布局*/}
                <Grid xs={2} md={2}>
                    <div className="test">
                        <Button variant="text" sx={{
                            color: 'white',
                            fontWeight: '900',
                            fontSize: 'large'}}>Sign in</Button>
                        <Button variant="outlined" sx={{
                            color: 'white',
                            fontWeight: '900',
                            fontSize: 'large'}}>Sign up</Button>
                    </div>
                </Grid>
                {/*//注册登陆按钮（导航栏居右）*/}
            </Grid>
        </Box>
    );
}

export {NavigationBar}