import { useEffect, useState } from "react";
import backendCall from "../JsFiles/BaseAxios";

export default function SearchComponent({setResult}) {
    const [searchTerm, setSearchTerm] = useState("");

    useEffect(() => {
        if (!searchTerm.trim()) {
            return;
        }
        const delayBounceFn = setTimeout(() => {
            backendCall.get(`/task/search`, {
                params: { word: searchTerm }
            }).then(res => setResult(res.data));
        }, 2000);
        return () => clearTimeout(delayBounceFn);
    }, [searchTerm]);

    return (
        <>
            <input
                type="text"
                placeholder="Search for task..."
                value={searchTerm}
                name="searchTerm"
                id="searchTerm"
                style={{
                    minWidth: '50%',
                    marginLeft: '20%',
                    marginRight: '3rem',
                    minHeight: '2rem',
                    borderRadius: '0.5rem',
                    textAlign: 'center'
                }}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
        </>
    )
}