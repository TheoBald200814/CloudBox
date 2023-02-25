import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';

import reportWebVitals from './reportWebVitals';
import MainPage from "./Main/Main";
import {BrowserRouter, NavLink, Route, Switch} from "react-router-dom";
import Home from "./Home/Home";
import UploadFile from "./Home/CloudBox/UploadFile";
import FileList from "./Home/CloudBox/FileList";
import DataTable from "./TestList";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(


    // <BrowserRouter>
    //
    //     <Switch>
    //         <Route path="/MainPage" component={MainPage} />
    //         <Route path="/Home" component={Home}/>
    //     </Switch>
    //
    // </BrowserRouter>
    <FileList />


);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();





