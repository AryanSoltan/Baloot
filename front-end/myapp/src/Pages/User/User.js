import * as React from "react";
import {toast} from "react-toastify";

import Footer from "../Footer"
import logo from "../../assets/images/ballot.png";
import {Link, Routes, Route, useNavigate} from 'react-router-dom';

import UserInfo from "./UserInfo"


import "../../Style/footer.css"
import "../../Style/User.css"
import Header from "../../components/Header/Header"
import Cart from "./Cart"
// import  "/bootstrap@4.6.2/dist/css/bootstrap.min.css"

import axios from 'axios'

export default class Login extends React.Component{



    constructor(props) {
        super(props);
        this.state = {
            userName: '',
            password: '',
        }
    }

    render() {
        return (
            <html lang="en">

            <head>
                <meta charSet="UTF-8"/>
                <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <title>Document</title>
            </head>

            <body>

            <Header/>
            <main className="mainBody">
            <UserInfo/>
                <Cart/>
            </main>
            <Footer/>
            </body>
            </html>
        );
    }
}
