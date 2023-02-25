import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';

import reportWebVitals from './reportWebVitals';
import MainPage from "./Main/Main";
import CloudBoxx from "./Home/CloudBox/CloudBox";
import {BrowserRouter, NavLink, Route, Switch} from "react-router-dom";
import Empty from "./Empty";
import SignInAndSignUp from "./Component/SignInAndSignUp";
import Appp from "./Empty";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  // <React.StrictMode>
  //
  //
  //   <Router>
  //     <Route path="/" component={MainPage} />
  //   </Router>
  //
  // </React.StrictMode>


    <BrowserRouter>

        <Switch>
            <Route path="/MainPage" component={MainPage} />
            <Route path="/Home" component={CloudBoxx}/>
        </Switch>

    </BrowserRouter>
    // <Appp />





);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();





