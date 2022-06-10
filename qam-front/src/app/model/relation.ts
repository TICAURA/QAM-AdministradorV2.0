export class Relation {
    father:number;
    child:number;
    build(content:any){
        this.father = content.father;
        this.child = content.child;
    }
}
