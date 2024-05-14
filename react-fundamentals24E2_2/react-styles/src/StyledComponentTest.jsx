import styled, { css } from "styled-components"

const StyledComponentTest = () => {
    const Title = styled.h1`
        color: red;
    `;

    const Container = styled.div`
    background: ${(props) => props.primary ? "orange" : "blue"};

    ${(props) =>
            props.testable && css`
            padding: 50px;
        `
        }

    .aclass {
        color: yellow;
    }
    `

    return (
        <Container primary testable={true}>
            <Title>StyledComponent</Title>
            <h2 className="aclass">A h2 text</h2>
        </Container>
    )
}

export default StyledComponentTest;
