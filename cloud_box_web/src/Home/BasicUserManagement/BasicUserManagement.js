import React from 'react';
import axios from "axios";
import {Button} from "@mui/material";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";
import Grid from "@mui/material/Unstable_Grid2";
import {DataGrid} from "@mui/x-data-grid";
import HistorySharpIcon from '@mui/icons-material/HistorySharp';
import AlertConfirm from "react-alert-confirm";

/**
 * 基础账户管理页面
 * @author TheoBald
 * @version 0.0.1
 */
export default class BasicUserManagement extends React.Component{

    constructor(props) {
        super(props);

        this.state = {
            token:props.token,
            basicAccountList:[]
        }
        this.updateBasicAccountList = this.updateBasicAccountList.bind(this);
        this.handleAccountDelete = this.handleAccountDelete.bind(this);
        this.handlePasswordInit = this.handlePasswordInit.bind(this);
        this.initPasswordSubmit = this.initPasswordSubmit.bind(this);
        this.deletedAccountSubmit = this.deletedAccountSubmit.bind(this);
    }
    componentDidMount() {
        this.updateBasicAccountList();
    }

    /**
     * 基础账户列表加载函数
     */
    updateBasicAccountList(){

        const {token} = this.state;
        axios.post("http://43.142.148.141:8081/selectBasicList?token=" + token ).then((response) => {
            console.log(response.data);
            if(response.data.res === 'failure'){
                alert("文件列表检索失败");
            }
            this.setState({
                basicAccountList:response.data
            })
        });
    }

    /**
     * 账户密码重置触发器
     */
    handlePasswordInit = async (params) => {

        const [action] = await AlertConfirm('确认重置' + params + '的密码');
        action && this.initPasswordSubmit(params);
    }

    /**
     * 账户重置密码提交
     */
    initPasswordSubmit(id){

        axios.post("http://43.142.148.141:8081/updateBasicPassword?token=" + this.state.token + '&id=' + id ).then((response) => {
            console.log(response.data);
            if(response.data.res === 'success'){
                alert("账户密码重置成功");
            }else {
                alert("账户密码重置失败");
            }
        });
    }

    /**
     * 账户注销触发器
     * @param id
     */
    handleAccountDelete = async (params) => {

        const [action] = await AlertConfirm('确认注销' + params);
        action && this.deletedAccountSubmit(params);
    }

    /**
     * 账户注销提交
     */
    deletedAccountSubmit(id){

        axios.post("http://43.142.148.141:8081/deleteBasicAccount?token=" + this.state.token + '&id=' + id ).then((response) => {
            console.log(response.data);
            if(response.data.res === 'success'){
                alert("账户注销成功");
                this.updateBasicAccountList();
            }else {
                alert("账户注销失败");
            }
        });
    }

    render() {

        const columns = [
            { field: 'id', headerName: 'AccountID', width: 250 },
            { field: 'accountEmpty', headerName: 'Empty', width: 100, type: 'number' },
            { field: 'deleted', headerName: 'Deleted', width: 100 },
            {
                field: 'init',
                headerName: 'Init',
                width: 80,
                renderCell: (params) => (
                    <Button variant="contained" color="error" sx = {{ borderRadius:'8px' }} onClick={() => this.handlePasswordInit(params.id)}>
                        <HistorySharpIcon />
                    </Button>
                ),
            },
            {
                field: 'delete',
                headerName: 'Delete',
                width: 80,
                renderCell: (params) => (
                    <Button variant="contained" color="error" sx = {{ borderRadius:'8px' }} onClick={() => this.handleAccountDelete(params.id)}>
                        <DeleteOutlineIcon />
                    </Button>
                ),
            }
        ];
        const getRowId = (row) => row.id;
        return (
            <Grid container >
                <Grid xs={12} md={12}>
                    这里放账户列表
                    <DataGrid columns={columns}
                              rows={this.state.basicAccountList}
                              getRowId={getRowId}
                              pageSize={5}
                              rowsPerPageOptions={[5]}
                              checkboxSelection
                              sx={{
                                  height:'60vh',
                                  width:'73vw'
                              }}
                    />
                </Grid>
            </Grid>
        );
    }

}

