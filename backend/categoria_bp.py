from flask import Blueprint, request, jsonify
from db import get_categorias, get_categoria, create_categoria, update_categoria, delete_categoria

categoria_bp = Blueprint('categoria', __name__)

@categoria_bp.route('', methods=['GET'])
def get_all():
    categorias = get_categorias()
    return jsonify(categorias), 200

@categoria_bp.route('/<int:id_categoria>', methods=['GET'])
def get_one(id_categoria):
    categoria = get_categoria(id_categoria)
    if categoria:
        return jsonify(categoria), 200
    return jsonify({"error": "Categoria not found"}), 404

@categoria_bp.route('', methods=['POST'])
def create():
    data = request.json
    if not data or 'nombre' not in data:
        return jsonify({"error": "Missing required data"}), 400
    
    new_id = create_categoria(nombre=data['nombre'])
    return jsonify({"id_categoria": new_id}), 201

@categoria_bp.route('/<int:id_categoria>', methods=['PUT'])
def update(id_categoria):
    data = request.json
    if not data or 'nombre' not in data:
        return jsonify({"error": "Nombre is required"}), 400
    
    affected = update_categoria(id_categoria, nombre=data['nombre'])
    if affected > 0:
        return jsonify({"message": "Categoria updated"}), 200
    return jsonify({"error": "Categoria not found"}), 404

@categoria_bp.route('/<int:id_categoria>', methods=['DELETE'])
def delete(id_categoria):
    affected = delete_categoria(id_categoria)
    if affected > 0:
        return jsonify({"message": "Categoria deleted"}), 200
    return jsonify({"error": "Categoria not found"}), 404