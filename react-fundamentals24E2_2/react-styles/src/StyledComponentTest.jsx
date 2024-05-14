import styled, { css, ThemeProvider } from "styled-components"
import 'bootstrap/dist/css/bootstrap.min.css';
import { Button, Card } from "react-bootstrap";

const StyledComponentTest = () => {
    const Title = styled.h1`
        color: red;
    `;

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
                    <Button variant="warning" value={"Warning"}>Warning</Button>
                </Card>
            </Container>
        </ThemeProvider>
    )
}

export default StyledComponentTest;
