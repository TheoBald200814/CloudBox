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

        this.handleChangeId = this.handleChangeId.bind(this);
        this.handleChangePassword_1 = this.handleChangePassword_1.bind(this);
        this.handleChangePassword_2 = this.handleChangePassword_2.bind(this);
        this.handleSubmitLogin = this.handleSubmitLogin.bind(this);
        this.handleSubmitRegister = this.handleSubmitRegister.bind(this);
        this.handleChangeMode=this.handleChangeMode.bind(this);
    }


    handleChangeId(event) {
        this.setState({userId: event.target.value});
    }

    handleChangePassword_1(event) {
        this.setState({password_1: event.target.value});
    }

    handleChangePassword_2(event) {
        this.setState({password_2: event.target.value});
    }

    handleSubmitLogin(event) {

        const { userId,password_1 } = this.state;
        axios.post("http://localhost:8081/tempLogin", {accountId: userId,password:password_1}).then((response) => {
            console.log(response.data);
        });
        // alert('用户名:' + this.state.userId + '密码:' + this.state.password_1);
        event.preventDefault();
    }

    handleSubmitRegister(event) {

        const { userId,password_1,password_2 } = this.state;
        if(password_1 === password_2){
            //如果密码一致
            axios.post("http://localhost:8081/createAccount", {accountId: userId,password:password_1}).then((response) => {
                console.log(response.data);
            });
        }else {
            alert("两次输入密码不一致，请重新输入");
        }
        event.preventDefault();
    }

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

