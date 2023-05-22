import { Link } from 'react-router-dom'
import logo from "../assets/images/ballot.png"
import "../Style/register-sign.css"
import {toast} from "react-toastify";


import Footer from "./Footer"
import "../Style/footer.css"

import axios from 'axios'
import React from "react";


export default class SignUp extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            userName: '',
            password: '',
            birthDate: '',
            address: '',
            repeatedPassword: '',
            email: '',
        }
        this.handleSubmit = this.handleSubmit.bind(this)
        this.handleUsernameChange = this.handleUsernameChange.bind(this)
        this.handlePasswordChange = this.handlePasswordChange.bind(this)
        this.handleAddress = this.handleAddress.bind(this)
        this.handlePasswordRepeat = this.handlePasswordRepeat.bind(this)
        this.handleBirthDate = this.handleBirthDate.bind(this)
        this.handleEmail = this.handleEmail.bind(this);
    }

    handleSubmit(e) {
        e.preventDefault();
        if(!this.state.userName){
            toast.error('should fill the username part');
            return
        }
        else if(!this.state.password)
        {
            toast.error('should fill the password part');
        }
        else if(!this.state.repeatedPassword)
        {
            toast.error('should repeat the password');
        }
        else if(this.state.repeatedPassword != this.state.password)
        {
            toast.error('repeated password is not equal to the main password');
        }
        else
        {
            axios.post('/signup', {
                username: this.state.userName,
                password: this.state.password,
                birthDate: this.state.birthDate,
                email: this.state.email,
                address: this.state.address,
            }).then((resp) => {
                if(resp.status === 200) {
                    localStorage.setItem('userLoggedIn', true);
                    localStorage.setItem('userId', this.state.userName);
                    window.location.href = "http://localhost:3000/commodities"
                }
            }).catch(error => {
                toast.error("Something went wrong!");
                console.log(error.toJSON().message)
            })
        }
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

    handleAddress(e)
    {
        this.setState(
            {
                address: e.target.value
            }
        );
    }

    handleBirthDate(e)
    {
        this.setState(
            {
                birthDate: e.target.value
            }
    );
    }

    handlePasswordRepeat(e)
    {
        this.setState(
            {
                repeatedPassword: e.target.value
            }
    );
    }

    handleEmail(e)
    {
        this.setState(
            {
                email: e.target.value
            }
    );
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


                <h1>Registeration Form </h1>
                <form onSubmit={this.handleSubmit}>
                <p><b>Username</b></p>

                <input type="text" placeholder="Enter Username" name="username" id="username"
                       onChange={this.handleUsernameChange}/>
                <br/>
                <p><b>Email</b></p>

                <input type="text" placeholder="Enter Email" name="email" id="email"
                       onChange={this.handleEmail}/>

                <br/>

                <p><b>Address</b></p>

                <input type="text" placeholder="Enter Address" name="address" id="address"
                       onChange={this.handleAddress}/>

                <br/>

                <p><b>Birth Date</b></p>

                <input type="text" placeholder="Enter Birth Date" name="birth-date" id="address"
                       onChange={this.handleBirthDate}/>

                <br/>

                <p><b>Password</b></p>

                <input type="password" placeholder="Enter Password" name="password"
                       id="password"  onChange={this.handlePasswordChange}/>

                <br/>

                <p><b>Repeat Password</b></p>

                <input type="password" placeholder="Repeat Password" name="email"
                       id="repeat-passs"  onChange={this.handlePasswordRepeat}/>

                <br/>

                <button className="register-button">
                    Register
                </button>
                </form>

                <div>
                    <p>Already have an account? <a href="/">Sign
                        in</a>.</p>
                </div>
            </div>
            <Footer/>
            </body>
            </html>
        )
    }
}