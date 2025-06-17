from flask import Blueprint, request, jsonify
from db import get_clientes, get_cliente, create_cliente, update_cliente, delete_cliente

cliente_bp = Blueprint('cliente', __name__)

@cliente_bp.route('', methods=['GET'])
def get_all():
    clientes = get_clientes()
    return jsonify(clientes), 200

@cliente_bp.route('/<int:id_cliente>', methods=['GET'])
def get_one(id_cliente):
    cliente = get_cliente(id_cliente)
    if cliente:
        return jsonify(cliente), 200
    return jsonify({"error": "Cliente not found"}), 404

@cliente_bp.route('', methods=['POST'])
def create():
    data = request.json
    if not data or 'nombre' not in data:
        return jsonify({"error": "Missing required data"}), 400
    
    new_id = create_cliente(
        nombre=data['nombre'],
        direccion=data.get('direccion'),
        telefono=data.get('telefono'),
        email=data.get('email')
    )
    return jsonify({"id_cliente": new_id}), 201

@cliente_bp.route('/<int:id_cliente>', methods=['PUT'])
def update(id_cliente):
    data = request.json
    if not data:
        return jsonify({"error": "No data provided"}), 400
    
    affected = update_cliente(
        id_cliente,
        **data
    )
    if affected > 0:
        return jsonify({"message": "Cliente updated"}), 200
    return jsonify({"error": "Cliente not found"}), 404

@cliente_bp.route('/<int:id_cliente>', methods=['DELETE'])
def delete(id_cliente):
    affected = delete_cliente(id_cliente)
    if affected > 0:
        return jsonify({"message": "Cliente deleted"}), 200
    return jsonify({"error": "Cliente not found"}), 404

# Fragmented views
@cliente_bp.route('/cdmexico', methods=['GET'])
def get_cdmexico():
    clientes = get_clientes_cdmexico()
    return jsonify(clientes), 200

@cliente_bp.route('/guadalajara', methods=['GET'])
def get_guadalajara():
    clientes = get_clientes_guadalajara()
    return jsonify(clientes), 200