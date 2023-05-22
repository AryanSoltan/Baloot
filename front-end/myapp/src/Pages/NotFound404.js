import * as React from "react";
import {toast} from "react-toastify";

import axios from 'axios';

import "../Style/footer.css"
import "../Style/extra-pages.css"
import logo from '../assets/images/ballot.png';


export default class NotFound404 extends React.Component{

    constructor(props) {
        super(props);
    }

    render() {
        return (
            // <head>
            // <meta charset="UTF-8"/>
            // <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
            // <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
            // <title>Document</title>
            //
            // </head>
            <html lang="en">

            <body>
            <div class = "logo">
                <div class = "font-title"><img src={logo} alt="logo"></img></div>
            </div>
            </body>
            <h1>
                404 Page Not Found!
            </h1>
            <a href = "/">Back to Home Page</a>
            <footer class = "footer">
                <p>2023 @UT</p>
            </footer>
        </html>
    );
    }
}
