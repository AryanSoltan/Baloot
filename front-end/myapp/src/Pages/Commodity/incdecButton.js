import React, {useEffect, useState} from "react";
import './incdecButton.css';
import axios from "axios";
import commodity from "./Commodity";
export default function IncrementDecrement(props)
{

    const {commodityId, currentCount, max} = props;

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
        setCounter(value);
        try {
            const data = { userId: userId };
            const response = await axios.post('/users/' + commodityId+ '/remove/'+data);

        } catch (e) {
            console.log(e);
        }

    };

    const handleIncrement = async () => {
        if(isDisabled) return;
        let value = validateValue(counter + 1);
        setCounter(value);
        try {

            const data = { userId: userId };
            const response = await axios.post('/users/' + commodityId+ '/add/'+data);

        } catch (e) {
            console.log(e);
        }
    };

    const handleChange = (e) => {
        let value = (e.target.value ? parseInt(e.target.value) : 0);

        value = validateValue(value);


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

// IncrementDecrement.defaultProps = {
//     min: 5,
//     max: 100,
//     step: 3
// };
//
// export default IncrementDecrement;