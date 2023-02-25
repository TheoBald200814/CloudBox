
import * as React from 'react';

export default class HomePage extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            pageNum : props.pageNum,
            token:233
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.pageNum !== this.props.pageNum) {
            this.changePage(nextProps.pageNum)
        }
    }

    //函数里可以包含ajax请求等复杂的逻辑
    changePage(myArgument) {
        this.setState({
            pageNum: myArgument
        })
        this.changeTokenTest();
        console.log(this.state.pageNum);
    }

    changeTokenTest(){
        const {temp} = this.state;
        this.setState({
            token:temp +1
        })
    }

    render() {
        switch (this.state.pageNum){
            case '1':
                return (
                    <div>
                        PAGE1
                        {this.state.token}
                    </div>
                );
                break;
            case '2':
                return (
                    <div>
                        PAGE2
                        {this.state.token}
                    </div>

                );
                break;
            case '3':
                return (
                    <div>
                        PAGE3
                        {this.state.token}
                    </div>
                );
                break;
        }
    }

}

