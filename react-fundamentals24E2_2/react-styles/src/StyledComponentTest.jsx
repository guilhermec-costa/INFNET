import styled, { css, ThemeProvider } from "styled-components"
import 'bootstrap/dist/css/bootstrap.min.css';
import { Button, Card } from "react-bootstrap";
import { useEffect } from "react";

const StyledComponentTest = ({ buttonProps, user }) => {
    const Title = styled.h1`
        color: red;
    `;

    useEffect(() => {
        console.log("Usuário alterado")
    }, [user])

    const Container = styled.div`
    background: ${(props) => props.theme.main};

    ${(props) =>
            props.testable && css`
            padding: 50px;
        `
        }

    .aclass {
        color: yellow;
    }
    `

    const theme = {
        main: "grey"
    }

    return (
        <ThemeProvider theme={theme}>
            <Container primary testable={true}>
                <Card>
                    <Title>StyledComponent</Title>
                    <h2 className="aclass warning">A h2 text</h2>
                    {user && (
                        < Button {...buttonProps} >Warning</Button>
                    )
                    }
                </Card>
            </Container>
        </ThemeProvider >
    )
}

export default StyledComponentTest;
