import React, { Component } from 'react';

class Item extends Component {
    constructor(props) {
        super(props)
        this.state = {
            content: ''
        }
    }
    componentWillReceiveProps(nextProps) {
        if (nextProps.fun1 !== this.props.fun1) {
            this.fun1(nextProps.myArgument)
        } else if (nextProps.fun2 !== this.props.fun2) {
            this.fun2(nextProps.myArgument)
        }
    }

    //函数里可以包含ajax请求等复杂的逻辑
    fun1(myArgument) {
        let { fun1 } = this.props
        this.setState({
            content: 'from fun1:' + myArgument
        })
        console.log('fun1执行次数:' + fun1)
    }
    fun2(myArgument) {
        let { fun2 } = this.props
        this.setState({
            content: 'from fun2:' + myArgument
        })
        console.log('fun2执行次数:' + fun2)
    }
    render() {
        let { content } = this.state
        return (
            <div>
                {content}
            </div>
        )
    }
}

//父组件通过props控制子组件执行不同的方法进行渲染
class Appp extends Component {
    constructor(props) {
        super(props)
        this.state = {
            fun1: 0,
            fun2: 0,
            myArgument: []
        }
    }
    render() {
        let { fun1, fun2, myArgument } = this.state
        return (
            <div>
                <Item fun1={fun1} fun2={fun2} myArgument={myArgument}/>
                <button onClick={this.handleFun1.bind(this)}>Fun1</button>
                <button onClick={this.handleFun2.bind(this)}>Fun2</button>
            </div>
        );
    }
}

//事件
Object.assign(Appp.prototype, {
    handleFun1() {
        let { fun1 } = this.state
        fun1 = fun1 + 1
        this.setState({
            fun1,
            myArgument: ['xu', 'tongbao']
        })
    },
    handleFun2() {
        let { fun2 } = this.state
        fun2 = fun2 + 1
        this.setState({
            fun2,
            myArgument: ['徐', '同保']
        })
    }
})


export default Appp