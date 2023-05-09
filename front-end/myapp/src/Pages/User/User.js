import * as React from "react";

import { Route, Routes, useNavigate } from 'react-router-dom';

import Footer from "../Footer"
//
//
import UserInfo from "./UserInfo"


import "../../Style/footer.css"
// import "../../Style/User.css"
import "./Cart.css"
import Header from "../../components/Header/Header"
import Cart from "./Cart"


import axios from 'axios'
import {Context} from "../../App";
import {useEffect, useState} from "react";

export default function User() {




    // constructor(props) {
        const [value, setValue] = useState(0);


        var isLoggedIn = localStorage.getItem('userLoggedIn');






        // super(props);
        // this.state = {
        //     userName: '',
        //     password: '',
        // }
    // }

    // render() {
        return (
            <html lang="en" >

            <head>
                <meta charSet="UTF-8"/>
                <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <title>Document</title>
            </head>

            <body >
            {/*style={popUpIsOpen ? {backgroundColor: "rgba(217, 217, 217, 0.5)" }:{backgroundColor: "white"  }}*/}


            <Context.Provider value={{value, setValue}}>

            <Header/>
            <main >
            <UserInfo />
                <Cart/>
            </main>
            <Footer/>
            </Context.Provider>

            </body>
            </html>
        );
    // }
}
