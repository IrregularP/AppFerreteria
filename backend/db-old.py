import mysql.connector
from dataclasses import dataclass
from werkzeug.security import generate_password_hash, check_password_hash
from modelsOld import User


def get_db_connection():
    connection = mysql.connector.connect(
        user='root',
        password='root',
        host='localhost',
        database='Ferreteria',
        port=3307
    )
    return connection

def tupla_to_user(tupla: tuple) -> User:
    usuario = User(
        id=tupla[0],
        usuario=tupla[1],
        password=tupla[2],
        rol=tupla[3]
    )
    return usuario

def get_user_by_username(username: str) -> User:
    connection = get_db_connection()
    cursor = connection.cursor()
    cursor.execute("SELECT * FROM users WHERE usuario = %s", (username,))
    user_tuple = cursor.fetchone()
    cursor.close()
    connection.close()

    if user_tuple:
        return tupla_to_user(user_tuple)
    return None
