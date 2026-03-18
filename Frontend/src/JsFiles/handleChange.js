
export function handleChange(e, formData, setFormData) {
    setFormData({...formData, [e.target.id]: e.target.value});
}