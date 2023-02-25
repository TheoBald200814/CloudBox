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
            rows:[]
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
    handleButtonDelete(id) {
        // console.log(`Button clicked for row with id ${id}`);
        alert(id + "Delete");
    }

    /**
     * 文件修改触发器
     * @param id
     */
    handleButtonChange(id) {
        // console.log(`Button clicked for row with id ${id}`);
        alert(id + "Change");
    }

    /**
     * 文件下载触发器
     * @param id
     */
    handleButtonDownload(id) {
        // console.log(`Button clicked for row with id ${id}`);
        alert(id + "Download");
    }

    render() {
        const columns = [
            { field: 'fileName', headerName: 'Name', width: 400 },
            { field: 'fileSize', headerName: 'Size', width: 100, type: 'number' },
            { field: 'fileType', headerName: 'Type', width: 100 },
            { field: 'fileDate', headerName: 'Date', width: 200, type: 'date'},
            { field: 'downloadCount', headerName: 'DownloadCount', width: 160 },
            {
                field: 'change',
                headerName: 'Change',
                width: 80,
                renderCell: (params) => (
                    <Button variant="outlined" color="primary" sx = {{ borderRadius:'8px' }} onClick={() => this.handleButtonChange(params.id)}>
                        <CreateOutlinedIcon />
                    </Button>
                ),
            },
            {
                field: 'download',
                headerName: 'Download',
                width: 80,
                renderCell: (params) => (
                    <Button variant="outlined" color="primary" sx = {{ borderRadius:'8px' }} onClick={() => this.handleButtonDownload(params.id)}>
                        <CloudDownloadOutlinedIcon />
                    </Button>
                ),
            },
            {
                field: 'share',
                headerName: 'Share',
                width: 80,
                renderCell: (params) => (
                    <Button variant="outlined" color="primary" sx = {{ borderRadius:'8px' }} onClick={() => this.handleButtonShare(params.id)}>
                        <ShareIcon />
                    </Button>
                ),
            },
            {
                field: 'delete',
                headerName: 'Delete',
                width: 80,
                renderCell: (params) => (
                    <Button variant="contained" color="error" sx = {{ borderRadius:'8px' }} onClick={() => this.handleButtonDelete(params.id)}>
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
                                  height:'50vh',
                                  width:'100vw'
                              }}
                    />
                </Grid>

            </Grid>

        );

    }


}


