public class Collision {

    private Particle p1, p2;
    
    private float c;
    
    private PVector contactNormal;
    
    public Collision(Particle p1, Particle p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.c = 1f;
        
        this.contactNormal = PVector.sub(p2.position,  p1.position).normalize();
    }
    
    public void resolveCollision() {
        float closingVel = calculateClosingVelocity();
        
        float newClosingVel = -closingVel * c;
        
        float deltaVel = newClosingVel - closingVel;
        
        float totalInverseMass = (1f/p1.mass) + (1f/p2.mass);
        
        float impulse = deltaVel / totalInverseMass;
        
        PVector impulsePerInverseMass = contactNormal.get().mult(impulse);
        
        PVector p1Impulse = impulsePerInverseMass.get().mult((1f/p1.mass));
        PVector p2Impulse = impulsePerInverseMass.get().mult(-(1f/p2.mass));
        
        p1.velocity.add(p1Impulse);
        p2.velocity.add(p2Impulse);
    }
    
    public float calculateClosingVelocity() {
        float cv1 = p1.velocity.dot(PVector.sub(p2.position, p1.position).normalize());
        float cv2 = p2.velocity.dot(PVector.sub(p1.position, p2.position).normalize());
        
        float closingVel = cv1 + cv2;
        
        return closingVel;
    }
    
    
}