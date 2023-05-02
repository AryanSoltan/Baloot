import React, { useState } from "react"
import { useSearchParams } from "react-router-dom";
import './Navbar.css'

import axios from "axios";
export default function SearchbarHeader() {
//todo not complete
    const [filterBy, setFilterBy] = useState('name');
    const [searchValue, setSearchValue] = useState('');

    const [searchParams, setSearchParams] = useSearchParams();

    const [open, setOpen] = React.useState(false);

    const handleOpen = () => {
        setOpen(!open);
    };

    const setFilter = (filterType) => {
        // do something
        setOpen(false);
        setFilterBy(filterType);


    };


    const handleSubmit = e => {

        e.preventDefault();
        if (searchValue=="") return;
        setSearchParams({
            filterBy,
            searchValue
        })
    }




    return (
        <>
            <div className="searchbar-container search-bar search-box">
                <head>
                    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
                </head>

            {/*<form*/}


            {/*    // onSubmit={handleSubmit}*/}
            {/*    className="search-bar search-box"*/}
            {/*>*/}
                <div className="dropdown">
                    <button onClick={handleOpen}>
                         <span>{filterBy}
            <i className='fa fa-chevron-down'></i>
        </span>

                    </button>
                    {open ? (
                        <ul className="menu">
                            <li className="menu-item">
                                <a onClick={() => { setFilter('name'); }}>name</a>
                            </li>
                            <li className="menu-item">
                                <a onClick={() => { setFilter('provider'); }}>provider</a>
                            </li>
                            <li className="menu-item">
                                <a onClick={() => { setFilter('category'); }}>category</a>
                            </li>

                        </ul>
                    ) : null}
                </div>

                <input
                    type="text"
                    className="form-control" name="x" placeholder="search your product..."
                    value={searchValue}
                    onChange={e => { setSearchValue(e.target.value); }}
                />
                <span className="search-icon-holder"><button className="btn search-icon"  onClick={handleSubmit}></button></span>
            {/*</form>*/}
            </div>

        </>
    );
}
