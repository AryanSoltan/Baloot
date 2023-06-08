import * as React from "react";
import {toast} from "react-toastify";

import Footer from "./Footer"
import logo from "../assets/images/ballot.png";
import {Link, Routes, Route, useNavigate} from 'react-router-dom';

import {useEffect, useState} from 'react';
import "../Style/register-sign.css"
import "../Style/footer.css"

import axios from 'axios'

export default function Login() {

    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    useEffect(() => {

      localStorage.clear();
      console.log("in local storage clear");

    }, []);
    const handleSubmit = async(e)=> {
        console.log("HELOOOO");
        e.preventDefault();
        console.log("email is ");
        console.log(email);
        console.log(password);
        if(!email){
            toast.error('should fill the username part');
            return
        }
        else if(!password)
        {
            toast.error('should fill the password part');
        }
        else
        {
            try{

                const response = await axios.post('/login', {
                    email: email,
                    password: password
                })

                console.log(response);

                if(response.data.statusCode == 200) {
                    localStorage.setItem('userLoggedIn', true);
                    localStorage.setItem('userEmail', email);
                    localStorage.setItem('token', response.data.content);
                    console.log("token is: ");
                    console.log(localStorage.getItem('token'));
                    console.log("resp is ");

                    //navigate("/commodities");
                }
                  try {
                        console.log('getting username');
                        const response = await axios.post("/usernameForLogin", {email: email});
                        const username = response.data.content;

                        localStorage.setItem('userId', username);

                        window.location.href = "http://localhost:3000/commodities";
                    }
                    catch (e) {
                        console.log(e);
                    }
            }

            catch(e){
                toast.error("password or email is wrong");
                console.log(e.message)
            }
        }
    }


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
                <form onSubmit={handleSubmit}>

                    <h1>Sign in Form </h1>

                    <p>Email</p>

                    <input type="text" placeholder="Enter Email" name="email" id="email" onChange={e => { setEmail(e.target.value) }}/>
                    <br/>
                    <br/>
                    <p>Password</p>

                    <input type="password" placeholder="Enter Password" name="password" id = "password"  onChange={e => { setPassword(e.target.value) }}/>

                    <br/>

                    <button className="register-button">
                        Sign in
                    </button>
                </form>

                <div>
                    <p>Dont' have an account? <a href="/signup">Create New Account</a>.</p>
                </div>
                <div>
                    <a href = "https://github.com/login/oauth/authorize?client_id=19aafd1365dd0bff246e&scope=user">Login with github</a>
                </div>
            </div>


            <Footer/>
            </body>
            </html>
        );

}
