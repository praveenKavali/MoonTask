import useSWR from "swr";
import backendCall from "./BaseAxios";

export async function patchCompleted(url, {arg}) {
    return await backendCall.patch(`${url}/${arg}`).then(res => res.data);
}

export function useAllTask() {
    const get = async (url) => {
        try {
            const data = await backendCall(url).then(res=> res.data);
            return data;
        } catch(e) {
            console.log(`Error during get all task ${e}`);
        }
    }
    return useSWR( '/task/all', get, {
        revalidateOnFocus: false,
    });
}
