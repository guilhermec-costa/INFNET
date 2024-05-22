import React from "react";
import * as S from "./style"
import { useState, useEffect } from "react";

const Home = () => {
    const [x, setX] = useState(2);
    const [y, setY] = useState(x);
    useEffect(() => {
        console.log(y)
    }, [x])

    return (
        <div>
            <S.Container color="blue">Home</S.Container>
            <button onClick={() => {
                setX(prev => prev * 2);
                setY(prev => prev * 2);
            }}>click me</button>
        </div>
    )
}

export default Home;
