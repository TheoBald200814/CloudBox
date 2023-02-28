import React from 'react';
import Box from "@mui/material/Box";
import Grid from "@mui/material/Unstable_Grid2";
import UploadFile from "./UploadFile";
import axios from "axios";
import {DataGrid} from "@mui/x-data-grid";
import {Button, Input} from "@mui/material";
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';
import CreateOutlinedIcon from '@mui/icons-material/CreateOutlined';
import CloudUploadOutlinedIcon from '@mui/icons-material/CloudUploadOutlined';
import CloudDownloadOutlinedIcon from '@mui/icons-material/CloudDownloadOutlined';
import ShareIcon from '@mui/icons-material/Share';
import AlertConfirm from 'react-alert-confirm';
import SignInAndSignUp from "../../Component/SignInAndSignUp";
import FileUpdate from "./ChildWindow/FileUpdate";

/**
 * 文件管理界面
 */
export default class FileList extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            token:'7c8fc5d4d75bf8312d352c1eca738b16',
            file:null,
            fileName:'',
            fileList:'',
            rows:[],
            testPage:false,
            visible:false

        }
        this.updateFileList = this.updateFileList.bind(this);
    }

    componentDidMount() {
        this.updateFileList();
    }

    /**
     * 文件更新函数
     * @param event
     */
    handleFileChange = (event) => {
        const files = event.target.files[0];
        const reader = new FileReader();
        this.setState({
            file: files,
            fileName:files.name
        });
    };

    /**
     * 文件上传函数
     */
    handleFileUpload = () => {
        const {token, fileName} = this.state;
        const formData = new FormData();
        if(this.state.file != null){
            formData.append("file", this.state.file);
            axios.post("http://localhost:8082/createFile?token=" + token + "&fileName=" + fileName, formData).then((response) => {
                console.log(response.data);
                if(response.data.res === 'success'){
                    alert("文件上传成功");
                    this.updateFileList();
                }else {
                    alert("文件上传失败");
                }
            });
        }else {
            alert("请选择文件");
        }

    };

    /**
     * 个人文件列表加载函数
     */
    updateFileList(){

        const {token} = this.state;
        axios.post("http://localhost:8082/readFileList?token=" + token ).then((response) => {
            console.log(response.data);
            if(response.data.res === 'failure'){
                alert("文件列表检索失败");
            }
            this.setState({
                rows:response.data
            })
        });
    }

    /**
     * 文件删除触发器
     * @param id
     */
    handleFileDelete(id) {

        const {token} = this.state;

        axios.post("http://localhost:8082/deleteFile", {token: token, fileName:id}).then((response) => {
            //建立连接，删除文件
            if(response.data.res === 'success'){
                //若删除成功
                alert("文件名删除成功");
            }else {
                //若删除失败
                alert("文件名删除失败");
            }
        });
    }

    /**
     * 文件修改触发器
     * @param id
     */
    handleFileChange(id) {
        this.setState({
            testPage:true
        })
    }

    /**
     * 文件更新触发器
     * @param params
     * @returns {Promise<void>}
     */
    handleFileUpdate = async (params) => {

        const [action] = await AlertConfirm({
            maskClosable: true,
            custom: dispatch => (
                <div>
                    <FileUpdate token = {this.state.token} fileName = {params} />
                </div>
            )
        });
    }

    /**
     * 文件下载触发器
     * @param id
     */
    handleFileDownload(id) {

        axios({
            url: `http://localhost:8082/downloadFile/?token=` + this.state.token + '&fileName=' + id,
            method: 'GET',
            responseType: 'blob', // 指定响应类型为二进制流
        }).then((response) => {
            // 创建一个URL对象，用于生成下载链接
            const url = window.URL.createObjectURL(new Blob([response.data]));
            // 创建一个<a>标签，用于触发下载
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', id);
            document.body.appendChild(link);
            link.click();
        });


    }


    render() {
        const columns = [
            { field: 'fileName', headerName: 'Name', width: 250 },
            { field: 'fileSize', headerName: 'Size', width: 80, type: 'number' },
            { field: 'fileType', headerName: 'Type', width: 50 },
            { field: 'fileDate', headerName: 'Date', width: 200, type: 'date'},
            { field: 'downloadCount', headerName: 'Download', width: 100 },
            {
                field: 'change',
                headerName: 'Change',
                width: 80,
                renderCell: (params) => (
                    <Button variant="contained" color="primary" sx = {{ borderRadius:'8px' }} onClick={() => this.handleFileUpdate(params.id)}>
                        <CreateOutlinedIcon />
                    </Button>
                ),
            },
            {
                field: 'download',
                headerName: 'Download',
                width: 80,
                renderCell: (params) => (
                    <Button variant="contained" color="primary" sx = {{ borderRadius:'8px' }} onClick={() => this.handleFileDownload(params.id)}>
                        <CloudDownloadOutlinedIcon />
                    </Button>
                ),
            },
            {
                field: 'share',
                headerName: 'Share',
                width: 80,
                renderCell: (params) => (
                    <Button variant="contained" color="primary" sx = {{ borderRadius:'8px' }} onClick={() => this.handleFileShare(params.id)}>
                        <ShareIcon />
                    </Button>
                ),
            },
            {
                field: 'delete',
                headerName: 'Delete',
                width: 80,
                renderCell: (params) => (
                    <Button variant="contained" color="error" sx = {{ borderRadius:'8px' }} onClick={() => this.handleFileDelete(params.id)}>
                        <DeleteOutlineIcon />
                    </Button>
                ),
            }


        ];
        const getRowId = (row) => row.fileName;

        return (

        <Grid container >

            <Grid xs={12} md={12}>
                <Input type="file" onChange={this.handleFileChange} />
            </Grid>

            <Grid xs={12} md={12} sx={{
                display:"flex",
                justifyContent: "center",
                alignItems: "center",
                padding:'2vh'
            }}>
                <Button variant="contained" color="primary" sx = {{ borderRadius:'8px' }} onClick={this.handleFileUpload}>
                    <CloudUploadOutlinedIcon />
                </Button>
            </Grid>

            <Grid xs={12} md={12}>
                这里放文件列表

                <DataGrid columns={columns}
                          rows={this.state.rows}
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


