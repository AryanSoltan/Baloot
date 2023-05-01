
import * as React from "react";
import {toast} from "react-toastify";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom"

import userPNG from "../assets/images/user-image.jpg"
import dollarPNG from "../assets/images/dollar.png"
import userInfoPNG from "../assets/images/map-user-info.png"
import emailPNG from "../assets/images/user-email.png"
import dateInfoPNG from "../assets/images/date-user-info.png"

// import 'bootstrap/dist/css/bootstrap.min.css';



import "../Style/User.css"

import axios from 'axios'
export default function UserInfo()
{
    const { id } = useParams();
    const [user, setUser] = useState();
    const [error, setError] = useState('');


    const navigate = useNavigate();



    useEffect(() => {

        const isLoggedIn = localStorage.getItem('userLoggedIn');
        const userId = localStorage.getItem('userId');
        async function fetchData() {
            try {
                const response = await axios.get('users/' + userId);
                const userR= response.data.content;

                console.log(userR);
                setUser(userR);
            } catch (e) {
                if(e.response.status === 404) {
                    navigate('/404');
                }
            }
        }

        fetchData();

    }, []);

    return(
            <section className="section-user-info">

                <div className="container-fluid p0">
                    <div className="row">

                        <div className="col-lg-6 col-md-12 first-row-info">
                            <div className="item-info">
                                <img src={userPNG}/>
                                <p className="user-info-txt">{user.email}</p>
                            </div>
                            <div className="item-info">
                                <img src={emailPNG}/>
                                <p className="user-info-txt">Marshal.Mathers@gmail.com</p>
                            </div>
                            <div className="item-info">
                                <img src={dateInfoPNG}/>
                                <p className="user-info-txt">1972/10/17</p>
                            </div>
                            <div className="item-info">
                                <img src={userInfoPNG}/>
                                <p className="user-info-txt">20785 Schultes Avenue, Warren, MI 48091</p>
                            </div>
                        </div>

                        <div className="col-lg-6 col-md-12">
                            <div className="credit-info">
                                <img src={dollarPNG}/>
                                <p className="credit-info-txt">1000000</p>
                            </div>
                            <input className="box-amount" type="text" id="fname" name="fname"
                                   placeholder="$ Amount"></input>
                            <button type="button" className="button-credit">
                                <p>Add more credit!</p>
                            </button>
                        </div>

                    </div>

                </div>
            </section>

    )
}