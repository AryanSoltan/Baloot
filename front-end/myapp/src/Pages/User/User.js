import * as React from "react";

import Footer from "../Footer"
//
//
import UserInfo from "./UserInfo"


import "../../Style/footer.css"
import "../../Style/User.css"
import Header from "../../components/Header/Header"
import Cart from "./Cart"


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
            <main >
            <UserInfo/>
                <Cart/>
            </main>
            <Footer/>
            </body>
            </html>
        );
    }
}
