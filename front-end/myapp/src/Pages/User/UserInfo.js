
import * as React from "react";
import {toast} from "react-toastify";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom"

import userPNG from "../../assets/images/user-image.jpg"
import dollarPNG from "../../assets/images/dollar.png"
import userInfoPNG from "../../assets/images/map-user-info.png"
import emailPNG from "../../assets/images/user-email.png"
import dateInfoPNG from "../../assets/images/date-user-info.png"


import Popup from 'reactjs-popup';

// import 'bootstrap/dist/css/bootstrap.min.css';



import "../../Style/User.css"
import "./Cart.css"

import axios from 'axios'
export default function UserInfo()
{
    const { id } = useParams();
    const [user, setUser] = useState('');
    const [error, setError] = useState('');
    const [credit, setCredit] = useState('');


    const navigate = useNavigate();



    useEffect(() => {

        const isLoggedIn = localStorage.getItem('userLoggedIn');
        const userId = localStorage.getItem('userId');

        async function fetchData() {
            try {
                const response = await axios.get('users/' + userId);
                const userR = response.data.content;

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
    }

    const handleLogOut = (e) => {
        localStorage.setItem('userLoggedIn', null);
        localStorage.setItem('userId', null);
        window.location.href = "http://localhost:3000/";
    }

    const addCredit = (e) =>
    {
        
        console.log(2);

        const userId = localStorage.getItem('userId');


        try {
            const response = axios.post('/users/' + userId + '/buyList/addCredit',
                {
                    credit: credit
                });
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
                                {/*<img src={dollarPNG}/>*/}$
                                {user.credit}

                            </div>
                            <input
                                onChange={e => { setCredit(e.target.value);  }}
                                className="form-control credit-input-box"
                            />
                            <button type="submit" className="credit-button" onClick={()=>{addCredit();}}>
                                <p>Add more credit!</p>
                            </button>


                                {/*<Popup trigger=*/}
                                {/*           {*/}
                                {/*               <form onSubmit={addCredit}>*/}
                                {/*                   <input className="box-amount" type="text" id="fname" name="fname"*/}
                                {/*                          placeholder="$ Amount" onChange={creditChange}></input>*/}
                                {/*                   <button type="submit" className="button-credit">*/}
                                {/*                       <p>Add more credit!</p>*/}
                                {/*                   </button>*/}
                                {/*               </form>*/}
                                {/*               }*/}
                                {/*       modal nested>*/}
                                {/*    {*/}
                                {/*        close => (*/}
                                {/*            <div className="popup-window">*/}
                                {/*            <p>hello</p>*/}
                                {/*            </div>*/}
                                {/*        )*/}
                                {/*    }*/}
                                {/*</Popup>*/}

                        </div>



                </div>
            // </section>

    )
}