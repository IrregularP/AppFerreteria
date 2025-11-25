from flask import Blueprint, request, jsonify
from db import get_tipos_pago, get_tipo_pago, create_tipo_pago, update_tipo_pago, delete_tipo_pago

tipo_pago_bp = Blueprint('tipo_pago', __name__)

@tipo_pago_bp.route('', methods=['GET'])
def get_all():
    tipos = get_tipos_pago()
    return jsonify(tipos), 200

@tipo_pago_bp.route('/<int:id_tipo_pago>', methods=['GET'])
def get_one(id_tipo_pago):
    tipo = get_tipo_pago(id_tipo_pago)
    if tipo:
        return jsonify(tipo), 200
    return jsonify({"error": "Tipo pago not found"}), 404

@tipo_pago_bp.route('', methods=['POST'])
def create():
    data = request.json
    if not data or 'nombre' not in data:
        return jsonify({"error": "Missing required data"}), 400
    
    new_id = create_tipo_pago(
        nombre=data['nombre'],
        descripcion=data.get('descripcion')
    )
    return jsonify({"id_tipo_pago": new_id}), 201

@tipo_pago_bp.route('/<int:id_tipo_pago>', methods=['PUT'])
def update(id_tipo_pago):
    data = request.json
    if not data:
        return jsonify({"error": "No data provided"}), 400
    
    affected = update_tipo_pago(id_tipo_pago, **data)
    if affected > 0:
        return jsonify({"message": "Tipo pago updated"}), 200
    return jsonify({"error": "Tipo pago not found"}), 404

@tipo_pago_bp.route('/<int:id_tipo_pago>', methods=['DELETE'])
def delete(id_tipo_pago):
    affected = delete_tipo_pago(id_tipo_pago)
    if affected > 0:
        return jsonify({"message": "Tipo pago deleted"}), 200
    return jsonify({"error": "Tipo pago not found"}), 404