import React from "react";
import axios from 'axios';
import './ComponentCSS/SignInAndSignUp.css'



/**
 * 注册登陆组件
 * @author TheoBald
 * @version 0.0.1
 */
export default class SignInAndSignUp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userId: '',
            password_1:'',
            password_2:'',
            login: true
        };
        //参数绑定
        this.handleChangeId = this.handleChangeId.bind(this);
        this.handleChangePassword_1 = this.handleChangePassword_1.bind(this);
        this.handleChangePassword_2 = this.handleChangePassword_2.bind(this);
        this.handleSubmitLogin = this.handleSubmitLogin.bind(this);
        this.handleSubmitRegister = this.handleSubmitRegister.bind(this);
        this.handleChangeMode=this.handleChangeMode.bind(this);
    }

    //账户Id设置
    handleChangeId(event) {
        this.setState({userId: event.target.value});
    }
    //账户密码设置
    handleChangePassword_1(event) {
        this.setState({password_1: event.target.value});
    }
    //账户核验密码设置
    handleChangePassword_2(event) {
        this.setState({password_2: event.target.value});
    }

    /**
     * 登陆触发器
     * @param event
     */
    handleSubmitLogin(event) {
        const { userId,password_1 } = this.state;
        //账户Id和密码
        if(userId == '') {
            //若userId为空
            alert("请输入账户ID");
        }else {
            if(password_1 == ''){
                //若密码为空
                alert("请输入密码");
            }else {
                //若输入均符合要求
                axios.post("http://43.142.148.141:8085/login", {accountId: userId,password:password_1}).then((response) => {
                    //建立连接，核验身份
                    if(response.data.res === "success"){
                        //若身份认证成功
                        window.location.assign(
                            '/Home'+
                            '?userId='+ userId +
                            '&token=' + response.data.token +
                            '&nickname=' + response.data.nickname +
                            '&authority=' + response.data.authority +
                            '&empty=' + response.data.empty
                        );
                        alert('登陆成功');
                        //跳转Home，携带所需参数
                    }else {
                        //若身份认证失败
                        alert('登陆失败');
                    }
                });

            }
        }
        event.preventDefault();
    }

    /**
     * 注册触发器
     * @param event
     */
    handleSubmitRegister(event) {
        const { userId,password_1,password_2 } = this.state;
        //账户Id、密码、核验密码
        if(userId == ''){
            //若账户Id为空
            alert("请输入账户ID");
        }else {
            if(password_1 == '' || password_2 == ''){
                //若账户密码或核对密码为空
                alert("请输入密码");
            }else {
                if(password_1 === password_2){
                    //若输入均符合要求
                    axios.post("http://43.142.148.141:8081/createAccount", {accountId: userId,password:password_1}).then((response) => {
                        //建立连接，注册账户
                        if(response.data.res === 'success'){
                            //若注册成功
                            alert("账户注册成功");
                        }else {
                            //若注册失败
                            alert("账户注册失败");
                        }
                    });
                }else {
                    alert("两次输入密码不一致，请重新输入");
                }

            }
        }
        event.preventDefault();
    }

    /**
     * 账户（注册-登陆切换）触发器
     * @param event
     */
    handleChangeMode(event){
        this.setState({
            login:!this.state.login,
            userId:'',
            password_1:'',
            password_2:''
        });
    }

    render() {
        if(this.state.login){
            return (
                <div className="container">
                    <div className="drop">
                        <div className="content">
                            <h2 style={{color:'white'}}>登  陆</h2>
                            <form onSubmit={this.handleSubmitLogin}>
                                <div className="inputBox">
                                    <input type="text" value={this.state.userId} onChange={this.handleChangeId} placeholder="用户名" style={{caretColor:'white'}}/>
                                </div>
                                <div className="inputBox">
                                    <input type="password" value={this.state.password_1} onChange={this.handleChangePassword_1}
                                           placeholder="密码"style={{caretColor:'white'}}/>
                                </div>
                                <div className="inputBox">
                                    <input type="submit" value="登陆"/>
                                </div>
                            </form>
                        </div>
                    </div>
                    <a href="my-app/src/LoginAndRegister/LoginAndRegister#" className="btns">忘记密码</a>
                    <div className="btns signup" onClick={this.handleChangeMode}>注册</div>
                </div>
            );

        }else{
            return (
                <div className="container">
                    <div className="drop">
                        <div className="content">
                            <h2 style={{color:'white'}}>注  册</h2>
                            <form onSubmit={this.handleSubmitRegister}>
                                <div className="inputBox">
                                    <input type="text" value={this.state.userId} onChange={this.handleChangeId} placeholder="用户名"style={{caretColor:'white'}}/>
                                </div>
                                <div className="inputBox">
                                    <input type="password" value={this.state.password_1} onChange={this.handleChangePassword_1}
                                           placeholder="密码"style={{caretColor:'white'}}/>
                                </div>
                                <div className="inputBox">
                                    <input type="password" value={this.state.password_2} onChange={this.handleChangePassword_2}
                                           placeholder="确认密码"style={{caretColor:'white'}}/>
                                </div>
                                <div className="inputBox">
                                    <input type="submit" value="注册"/>
                                </div>
                            </form>
                        </div>
                    </div>
                    <a href="my-app/src/LoginAndRegister/LoginAndRegister#" className="btns">忘记密码</a>
                    <div className="btns signup" onClick={this.handleChangeMode}>登陆</div>
                </div>
            );
        }
    }
}

