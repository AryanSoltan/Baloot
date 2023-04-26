import * as React from "react";
import {toast} from "react-toastify";

import Footer from "./Footer"
import logo from "../assets/images/ballot.png";

import "../Style/register-sign.css"
import "../Style/footer.css"

import axios from 'axios';

export default class Login extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            userName: '',
            password: '',
        }
        this.handleSubmit = this.handleSubmit.bind(this)
        this.handleUsernameChange = this.handleUsernameChange.bind(this)
        this.handlePasswordChange = this.handlePasswordChange.bind(this)
    }

    handleUsernameChange(e) {
        this.setState(
        {
            userName: e.target.value
        });
    }

    handlePasswordChange(e)
    {
        this.setState(
            {
                password: e.target.value
            }
        );
    }

    handleSubmit(e) {
        e.preventDefault();
        if(!this.state.userName){
            console.log('Username is empty')
            toast.error('should fill the username part');
            return
        }
        else if(!this.state.password)
        {
            toast.error('should fill the password part');
        }
        // else
        // {
        //     toast.success('200');
        // }
        // else
        // {
        //     // axios.post('auth/login/', {
        //     //     username: this.state.userName;
        //     // }).then((resp) => {
        //     //     if(resp.status === 200) {
        //     //         console.log('شد شد')
        //     //         toast.success('ورود با موفقیت انجام شد.')
        //     //         window.location.href = "http://localhost:3000/"
        //     //     }
        //     // }).catch(error => {
        //     //     console.log('نشد')
        //     //     toast.error('ایمیل نادرست است')
        //     // })
        // }
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

                <div className="font-title"><img src={logo}></img> Baloot</div>
                <form onSubmit={this.handleSubmit}>

                <h1>Sign in Form </h1>

                <p>Username</p>

                <input type="text" placeholder="Enter Username" name="username" id="username" onChange={this.handleUsernameChange}/>
                <br/>
                <br/>
                <p>Password</p>

                <input type="password" placeholder="Enter Password" name="password" id = "password" onChange={this.handlePasswordChange}/>

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

