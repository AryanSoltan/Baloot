
import * as React from "react";
import {toast} from "react-toastify";
import {useEffect, useMemo, useState} from "react";
import { Link, useNavigate, useParams } from "react-router-dom"

import userPNG from "../../assets/images/user-image.jpg"
import dollarPNG from "../../assets/images/dollar.png"
import userInfoPNG from "../../assets/images/map-user-info.png"
import emailPNG from "../../assets/images/user-email.png"
import dateInfoPNG from "../../assets/images/date-user-info.png"


import Popup from 'reactjs-popup';

// import 'bootstrap/dist/css/bootstrap.min.css';


//
import "../../Style/User.css"
import "./Cart.css"

import axios from 'axios'
export default function UserInfo()
{

    const { id } = useParams();
    const [user, setUser] = useState('');
    const [error, setError] = useState('');
    const [credit, setCredit] = useState(0);
    const navigate = useNavigate();



    useEffect(() => {

        const isLoggedIn = localStorage.getItem('userLoggedIn');
        const userId = localStorage.getItem('userId');

        async function fetchData() {
            try {
                console.log("hereeeeeeeeeeeeee");
                const response = await axios.get('users/' + userId);
                const userR = response.data.content;
                console.log("user is");

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


    const creditChange = (e) => {
        setCredit(e.target.value);
        console.log(e.target.value);
    };

    const handleLogOut = (e) => {
        localStorage.setItem('userLoggedIn', null);
        localStorage.setItem('userId', null);
        window.location.href = "http://localhost:3000/";
    };



    const addCredit = (e) =>
    {

        console.log(2);

        const userId = localStorage.getItem('userId');


        try {
            console.log("response is");
            const response = axios.post('/users/' + userId + '/buyList/addCredit',
                {
                    credit: credit
                });

            console.log(response);
                window.location.reload();

        } catch (e) {
            if(e.response.status === 404) {

                navigate('/404');
            }
        }

        // try {
        //     const response = axios.get('users/' + userId);
        //     const userR = response.data.content;
        //
        //     console.log(userR);
        //     setUser(userR);
        // } catch (e) {
        //     if(e.response.status === 404) {
        //         navigate('/404');
        //     }
        // }
    }


    return(
            // <section className="section-user-info">

                <div className="section-user-info">


                        <div className="col-1 first-row-info">
                            <div className="user-info-txt">
                                <img src={userPNG}/>
                               {user.name}
                            </div>
                            <div className="user-info-txt">
                                <img src={emailPNG}/>
                                {user.email}
                            </div>
                            <div className="user-info-txt">
                                <img src={dateInfoPNG}/>
                                {user.birthDate}
                            </div>
                            <div className="user-info-txt">
                                <img src={userInfoPNG}/>
                               {user.address}
                            </div>
                            <div className="user-info-txt">
                                <button type="submit" className="btn logout-button" onClick={handleLogOut}>logout</button>
                            </div>
                        </div>

                        <div className="col-1 price-part">
                            <div className="credit-info-txt">
                                ${user.credit}
                            </div>
                            <input
                                placeholder={"$Amount"}
                                onChange={e => { setCredit(e.target.value);  }}
                                className="form-control credit-input-box"
                            />
                            <Popup trigger=
                                       {     <button type="submit" className="credit-button" >Add more credit!</button>}
                                   modal nested>
                                {
                                    close => (

                                        <div className="popup-window">

                                            <h1>Add Credit</h1>
                                            <p>Are you sure you want to add {credit}$ to your account?</p>
                                            <div className="buttons">
                                                <button className="btn exit" onClick={() => {close(); } } >Close</button>
                                                <button type="submit" className="btn buy" onClick={addCredit } >Confirm!</button>
                                            </div>



                                        </div>
                                    )
                                }
                            </Popup>

                        </div>



                </div>
            // </section>

    )
}