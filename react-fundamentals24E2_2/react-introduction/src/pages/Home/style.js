import styled, { css } from "styled-components";

export const Container = styled.div`
    height: 40px;
    width: 40px;
    background: ${props => props.color || "red"};
`;
