import networkx as nx

class GraphUtils:
    def calculate_diameter(self, edges_str: str) -> str:
        if not edges_str:
            return "failed"

        edges = edges_str.split(",")

        G = nx.Graph()

        for edge in edges:
            if '-' in edge:
                node1, node2 = edge.split('-')
                G.add_edge(node1.strip(), node2.strip())

        if G.number_of_nodes() == 0:
            return "failed"

        if nx.is_connected(G):
            diameter = nx.diameter(G)
            return f"success: {diameter}"
        else:
            return "failed"
