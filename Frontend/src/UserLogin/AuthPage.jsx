
import { useState } from "react";
import { Form } from "../Components/Form";
import { handleChange } from "../JsFiles/handleChange";

export default function AuthPage({ handleSubmit, isRegister, isUpdating }) {
  const head = isRegister ? 'Register' : 'Login';
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: ""
  });

  return (
    <>
      <form onSubmit={handleSubmit}
        style={{
          textAlign: "center",
          borderRadius: '0.5rem',
          borderColor: 'green',
          backgroundColor: 'white',
          color: "black",
          padding: '2.5rem',
          margin: '5% 25%'
        }}>
        <h1 style={{margin: '0'}}>{head}</h1>
        {isRegister && (
          <Form
            id="name"
            placeholder="Enter your name"
            value={formData.name}
            handleChange={e => handleChange(e, formData, setFormData)}
          />
        )}
        {!isUpdating && (
          <Form
            id="email"
            type="email"
            placeholder="Enter your email"
            value={formData.email}
            handleChange={e => handleChange(e, formData, setFormData)}
          />
        )}
        <Form
          id="password"
          type="password"
          placeholder="Enter your password"
          value={formData.password}
          handleChange={e => handleChange(e, formData, setFormData)}
        />
        <button type="submit"
          style={{
            border: '0.3rem solid black',
            padding: '0.5rem',
            marginTop: '1.5rem',
            borderRadius: '0.5rem',
            backgroundColor: 'black',
            color: 'white'
          }}
        >Submit</button>
      </form>
    </>
  );
}
