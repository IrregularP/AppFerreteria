from dataclasses import dataclass

@dataclass
class User:
    id: int
    usuario: str
    password: str
    rol: str
