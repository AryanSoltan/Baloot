import React, {useEffect, useState} from "react";
import './incdecButton.css';
import axios from "axios";
import {toast} from "react-toastify";

import commodity from "./Commodity";

// const myData = { countItem: 0 };
// export const ExampleContext = React.createContext(myData);

export default function IncrementDecrement(props)
{

    const {commodityId, currentCount, max} = props;
    const {isChanged, change} = useState(false);

    const [counter, setCounter] = useState(0);
    const [isDisabled, disable] = useState(false);
    const userId = localStorage.getItem('userId');

    // console.log('counter i ');
    // console.log(counter);
    useEffect(()=>{
        setCounter(currentCount);
        if(max==5) disable(true);

    },[currentCount]);

    const handleDecrement = async() => {
        if(isDisabled) return;
        let value = validateValue(counter - 1);
        if(value < 0)
        {
            toast.error("can't be less than 0");
        }
        if(value > max)
        {
            toast.error("Out of stock");
        }
        setCounter(value);
        try {
            const data = { userId: userId };
            const response = await axios.post('/users/' + commodityId+ '/remove', data);
            change(true);

        } catch (e) {
            console.log(e);
        }
         // window.location.reload();

    };

    const handleIncrement = async () => {
        if(isDisabled)
        {
            return;
        }
        let value = validateValue(counter + 1);
        // if(value <= 0)
        // {
        //     toast.error("can't be less than 0");
        // }
        if(value >= max)
        {
            toast.error("Out of stock");
        }
        setCounter(value);
        try {

            const data = { userId: userId };
            const response = await axios.post('/users/' + commodityId+ '/add', data);
            // change(true);

        } catch (e) {
            console.log(e);
        }
        // window.location.reload();
    };

    const handleChange = (e) => {
        let value = (e.target.value ? parseInt(e.target.value) : 0);

        value = validateValue(value);
        // ExampleContext.Provider.countItem = value;
        // console.log("counttt");
        // console.log(ExampleContext.Provider.countItem);
        // change(value)


        setCounter(value);
    };

    const validateValue = (value) => {
        if(value < 0 ) {
            var value = 0;
        }

        if(value > max) {
            value = max;
        }

        return value;
    };


    {
        return (




    <div className={"increment-decrement-disabled-"+isDisabled}  >

                <a className="count-btn count-down" type="button" onClick={handleDecrement}>{counter>0&&"-"}</a>
                <span type="number" name="counter" className="counter" onChange={handleChange} >
                    {counter==0 ? <div onClick={handleIncrement}>
                        Add to card</div> :counter}
                </span>
                <a className="count-btn count-up" type="button" onClick={handleIncrement}>{counter>0&&"+"}</a>


            </div>
        );
    }
};

// export const isChanged = React.createContext(
//     IncrementDecrement.isChanged // default value
// );

// IncrementDecrement.defaultProps = {
//     min: 5,
//     max: 100,
//     step: 3
// };
//
// export default IncrementDecrement;