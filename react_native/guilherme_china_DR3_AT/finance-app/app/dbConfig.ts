import * as SQLite from 'expo-sqlite';

const getDb = async () => {
    return await SQLite.openDatabaseAsync("database.db");
}

const createTableUser = async () => {
    try {
        const db = await getDb();
        await db.execAsync(`
            PRAGMA journal_mode = WAL;
            CREATE TABLE IF NOT EXISTS user (
                uid TEXT PRIMARY KEY NOT NULL, 
                email TEXT NOT NULL, 
                photoURL TEXT, 
                phoneNumber TEXT, 
            );
        `);
        console.log("table created");
    } catch (err) {
        console.log("failed to create table: ", err.message)
    }
}

const dropTable = async (table: string) => {
    try {
        const db = await getDb();
        await db.execAsync(`DROP TABLE ${table};`);
        console.log("table delete");
    } catch (err) {
        console.log("failed to drop table: ", err.message)
    }
}

const insert = async (table: string, data: any) => {
    try {
        const db = await getDb();
        const keys = Object.keys(data);
        const values = Object.values(data).filter((v) => v !== "");

        const columns = keys.filter((k) => data[k] !== "").join(", ");
        const interrogations = values.filter((v) => v !== "").map(() => '?').join(", ");

        const query = `INSERT INTO ${table} (${columns}) VALUES (${interrogations})`;

        await db.runAsync(query, values);
        console.log("Dado inserido com sucesso")
    } catch (err) {
        console.error("Error insert:", err)
    }
}

const select = async (table: string, columns: string[], where: string, many: boolean) => {
    try {
        const columnString: string = columns.join(", ");
        const whereString = where !== "" && where !== null && where !== undefined ? `where ${where}` : "";
        const db = await getDb();
        const query: string = `SELECT ${columnString} FROM ${table} ${whereString};`;

        if (many) {
            return await db.getAllAsync(query);
        }
        return await db.getFirstAsync(query);
    } catch (err) {
        console.error("Error select:", err)
    }
}

export {
    insert,
    createTableUser,
    dropTable,
    select
}