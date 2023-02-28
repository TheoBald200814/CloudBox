import * as React from 'react';
import Box from "@mui/material/Box";
import Grid from "@mui/material/Unstable_Grid2";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import SettingsOutlinedIcon from "@mui/icons-material/SettingsOutlined";
import LogoutOutlinedIcon from "@mui/icons-material/LogoutOutlined";
import BoxTopTabscss from './ComponentCSS/BoxTopTabscss.css'
import axios from "axios";


/**
 * CloudBox顶部导航栏
 * @returns {JSX.Element}
 * @constructor
 */
export default function BoxTopTabs(props) {

    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const handleChangeLogout = () => {

        window.location.assign(
            '/MainPage'
        );
    }

    return (
        <Box className="navigate_bar" >
            <Grid container>
                <Grid xs={3} md={3}>
                    <div className="bold_smooth_text">
                        Cloud Box
                    </div>

                </Grid>
                <Grid xs={7} md={7}>

                </Grid>
                <Grid xs={2} md={2} container className="containerbutton">
                    <Grid>
                        <Avatar alt="Remy Sharp" src="/static/images/avatar/1.jpg" />
                    </Grid>
                    <Grid>
                        <Button variant="outlined" >
                            <SettingsOutlinedIcon/>
                        </Button>
                    </Grid>
                    <Grid>
                        <Button variant="outlined" onClick={handleChangeLogout}>
                            <LogoutOutlinedIcon/>
                        </Button>
                    </Grid>

                </Grid>

            </Grid>

        </Box>
    );
}