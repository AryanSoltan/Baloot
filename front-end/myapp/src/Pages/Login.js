import * as React from "react";
import {toast} from "react-toastify";

import Footer from "./Footer"

import "../Style/register-sign.css"
import "../Style/footer.css"



export default class Login extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            userName: '',
            password: '',
        }
        // this.handleEmailChange = this.handleEmailChange.bind(this)
        // this.handlePasswordChange = this.handlePasswordChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    handleSubmit(e) {
        e.preventDefault();
        if(!this.state.userName){
            console.log('Username is empty')
            toast.error('should fill the username part')
            return
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
            <div className="logo">

                <div className="font-title"><img src="../assets/images/ballot.png"></img> Baloot</div>
                <form>

                <h1>Sign in Form </h1>

                <p>Username</p>

                <input type="text" placeholder="Enter Username" name="username" id="username"/>
                <br/>
                <br/>
                <p>Password</p>

                <input type="password" placeholder="Enter Password" name="password" id = "password"/>

                <br/>

                <button className="register-button">
                    Sign in
                </button>
            </form>

                <div>
                    <p>Dont' have an account? <a href="register.html">Create New Account</a>.</p>
                </div>
            </div>


            <Footer/>
            </body>
            </html>
    );
    }
}

