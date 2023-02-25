import React from 'react';


/**
 * 文件管理界面
 */
export default class FileList extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            token:props.token
        }
        this.updateFileList = this.updateFileList.bind(this);
    }

    componentDidMount() {
        this.updateFileList();

    }


    /**
     * 个人文件列表加载函数
     */
    updateFileList(){

    }





}