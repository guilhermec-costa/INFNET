import React from "react";
import { useEffect } from "react";

const TestComponent = ({ prop }) => {
    
    useEffect(() => {
            console.log("Dentro do test")
    }, [prop]);
    return (
        <div>
            COMPONENTE TEST {prop}
        </div>
    )
}

export default TestComponent;
